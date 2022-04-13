package com.kaikeba.idscloud.gateway.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.kaikeba.idscloud.common.core.constants.AppConstants;
import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.utils.IdsTraceContext;
import com.kaikeba.idscloud.gateway.property.VersionProperties;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
            ErrorCodeEnum.SERVER_ERROR_B0210.assertFalse(isBlockServer(name), "ToC服务不能调用ToB服务");

            String group;
            List<Instance> instances = new ArrayList<>();
            NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
            // 判断是否gray流量
            if (IdsTraceContext.isGray() && IdsTraceContext.getGrayNameSpace().isPresent()) {
                group = IdsTraceContext.getGrayNameSpace().get();
                instances = namingService.selectInstances(name, group, true);
                // gray组实例优先处理，不存在gray组则走default_group组
                if (!CollectionUtils.isEmpty(instances)) {
                    List<Instance> grayInstanceToChoose = instances.stream()
                            .filter(in -> StringUtils.equalsIgnoreCase(IdsTraceContext.getVersion().orElse(null),
                                    in.getMetadata().get(AppConstants.VERSION_KEY)))
                            .collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(grayInstanceToChoose)) {
                        return chooseInstance(grayInstanceToChoose);
                    } else {
                        return chooseInstance(instances);
                    }
                }
            }

            group = this.nacosDiscoveryProperties.getGroup();
            instances = namingService.selectInstances(name, group, true);
            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", name);
                return null;
            }
            List<Instance> instancesToChoose = instances;
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
            if (IdsTraceContext.getVersion().isPresent()) {
                String appVersion = IdsTraceContext.getVersion().get();
                List<Instance> versionMatchInstances = instancesToChoose.parallelStream()
                        .filter(instance -> appVersion.equals(instance.getMetadata().get(AppConstants.VERSION_KEY)))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(versionMatchInstances)) {
                    instancesToChoose = versionMatchInstances;
                } else {
                    Map<String, List<Instance>> instanceMap = instancesToChoose.parallelStream()
                            .collect(Collectors.groupingBy(t -> t.getMetadata().get(AppConstants.VERSION_KEY)));
                    if (IdsTraceContext.isGray()) {
                        instancesToChoose = instanceMap.entrySet().parallelStream()
                                .max((o1, o2) -> compare(o1.getKey(), o2.getKey()))
                                .map(Map.Entry::getValue)
                                .get();
                    } else {
                        // 非gray，去保险的小版本
                        instancesToChoose = instanceMap.entrySet().parallelStream()
                                .min((o1, o2) -> compare(o1.getKey(), o2.getKey()))
                                .map(Map.Entry::getValue)
                                .get();
                    }
                    log.warn("Can not find version={} match server", appVersion);
                }
            }
            return chooseInstance(instancesToChoose);
        } catch (Exception e) {
            log.warn("NacosRule error", e);
            return null;
        }
    }

    private NacosServer chooseInstance(List<Instance> instances) {
        Instance instance = ExtendBalancer.getHostByRandomWeight2(instances);
        return new NacosServer(instance);
    }

    private boolean isBlockServer(String targetServerName) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 判断目标服务是否为console服务
        if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, targetServerName)) {
            // 判断当前服务是否为console 或 gateway 服务，都不是则进制调用
            if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, versionProperties.getRegisterServerName())
                    || antPathMatcher.match(GATEWAY_SERVER_NAME_PATTERN, versionProperties.getRegisterServerName())) {
                return true;
            }
        }
        return false;
    }

    private int compare(String v1, String v2) {
        String[] v1Splits = v1.subSequence(1, v1.length()).toString().split(".");
        String[] v2Splits = v2.subSequence(1, v1.length()).toString().split(".");
        int result = 0;
        for (int index = 0; index < v1.length(); index++) {
            int v1int = Integer.parseInt(v1Splits[index]);
            int v2int = Integer.parseInt(v2Splits[index]);
            result = v1int - v2int;
            if (result != 0) {
                return result;
            }
        }
        return result;
    }
}
