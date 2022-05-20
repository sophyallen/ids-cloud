package com.kaikeba.idscloud.flux.rule;

import cn.hutool.core.comparator.VersionComparator;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.kaikeba.idscloud.common.core.constants.AppConstants;
import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.core.model.VersionInfo;
import com.kaikeba.idscloud.common.utils.IdsTraceContext;
import com.kaikeba.idscloud.flux.property.VersionProperties;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description: 优先版本匹配
 */
@Slf4j
public class VersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private VersionProperties versionProperties;

    private static final String CONSOLE_SERVER_NAME_PATTERN = "*console*";
    private static final String GATEWAY_SERVER_NAME_PATTERN = "*gateway*";

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();
            // 检查下游服务是否可以被本服务调用
            ErrorCodeEnum.SERVER_ERROR_B0210.assertFalse(isBlockServer(name), "ToC服务不能调用ToB服务");

            VersionInfo versionInfo = IdsTraceContext.getVersionInfo().orElse(new VersionInfo());
            String group;
            List<Instance> instances;
            // 判断是否gray流量
            boolean isGray = versionInfo.isGray();
            NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
            // gray 流量并且配置了gray组
            if (isGray && versionInfo.getGrayGroup() != null) {
                instances = getFromGrayGroup(versionInfo, name, namingService);
                if (!CollectionUtils.isEmpty(instances)) {
                    return chooseInstance(instances);
                }
            }

            group = this.nacosDiscoveryProperties.getGroup();
            instances = namingService.selectInstances(name, group, true);
            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", name);
                return null;
            }

            List<Instance> instancesToChoose = instances;
            // nacos 原有逻辑，取出集群同名的实例列表
            if (StringUtils.isNotBlank(clusterName)) {
                List<Instance> sameClusterInstances = instances.stream()
                        .filter(instance -> Objects.equals(clusterName, instance.getClusterName()))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                    instancesToChoose = sameClusterInstances;
                } else {
                    log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
                            name, clusterName, instances);
                }
            }

            // 按版本号分组服务实例
            Map<String, List<Instance>> instanceMap = instancesToChoose.parallelStream()
                    .collect(Collectors.groupingBy(t -> t.getMetadata().getOrDefault(AppConstants.VERSION_KEY,
                            AppConstants.DEFAULT_SERVER_VERSION)));
            // 灰度场景，获取最高版本服务实例
            if (versionInfo.isGray()) {
                instancesToChoose = instanceMap.entrySet().parallelStream()
                        .max((o1, o2) -> VersionComparator.INSTANCE.compare(o1.getKey(), o2.getKey()))
                        .map(Map.Entry::getValue)
                        .get();
                return chooseInstance(instancesToChoose);
            }
            // 请求版本不为空，优先获取同版本号的实例
            if (StringUtils.isNotBlank(versionInfo.getVersion())
                    && instanceMap.containsKey(versionInfo.getVersion())) {
                String appVersion = versionInfo.getVersion();
                List<Instance> versionMatchInstances = instancesToChoose.parallelStream()
                        .filter(instance -> appVersion.equals(instance.getMetadata().get(AppConstants.VERSION_KEY)))
                        .collect(Collectors.toList());
                return chooseInstance(versionMatchInstances);
            }

            // 非gray，优先选取版本低的,避免正常流量进入gray
            instancesToChoose = instanceMap.entrySet().parallelStream()
                    .min((o1, o2) -> VersionComparator.INSTANCE.compare(o1.getKey(), o2.getKey()))
                    .map(Map.Entry::getValue)
                    .get();
            return chooseInstance(instancesToChoose);
        } catch (Exception e) {
            log.warn("NacosRule error", e);
            return null;
        }
    }

    /**
     * 从 gray group 获取服务实例，优先获取版本匹配
     *
     * @param versionInfo
     * @param name
     * @param namingService
     * @return
     * @throws NacosException
     */
    private List<Instance> getFromGrayGroup(VersionInfo versionInfo, String name, NamingService namingService) throws NacosException {
        String group = versionInfo.getGrayGroup();
        List<Instance> instances = namingService.selectInstances(name, group, true);
        if (!CollectionUtils.isEmpty(instances)) {
            List<Instance> grayInstanceToChoose = instances.stream()
                    .filter(in -> StringUtils.equals(versionInfo.getVersion(),
                            in.getMetadata().get(AppConstants.VERSION_KEY)))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(grayInstanceToChoose)) {
                return grayInstanceToChoose;
            }

        }
        return instances;
    }

    private NacosServer chooseInstance(List<Instance> instances) {
        Instance instance = ExtendBalancer.getHostByRandomWeight2(instances);
        return new NacosServer(instance);
    }

    private boolean isBlockServer(String targetServerName) {
        if (!versionProperties.isCheckCallable()) {
            return true;
        }
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 判断目标服务是否为console服务
        if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, targetServerName)) {
            // 判断当前服务是否为console 或 gateway 服务，都不是则进制调用
            if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, versionProperties.getServerName())
                    || antPathMatcher.match(GATEWAY_SERVER_NAME_PATTERN, versionProperties.getServerName())) {
                return true;
            }
        }
        return false;
    }

}
