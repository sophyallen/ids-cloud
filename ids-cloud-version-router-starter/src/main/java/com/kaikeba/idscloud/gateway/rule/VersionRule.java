package com.kaikeba.idscloud.gateway.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.kaikeba.idscloud.common.core.constants.AppConstants;
import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.utils.LoginUtil;
import com.kaikeba.idscloud.gateway.property.VersionProperties;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description: 优先版本匹配
 */
@Slf4j
@RequiredArgsConstructor
public class VersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    private final VersionProperties versionProperties;
    private static final String CONSOLE_SERVER_NAME_PATTERN = "*console*";
    private static final String GATEWAY_SERVER_NAME_PATTERN = "*gateway*";

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            String group = this.nacosDiscoveryProperties.getGroup();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();
            ErrorCodeEnum.SERVER_ERROR_B0210.assertFalse(isBlockServer(name), "ToC服务不能调用ToB服务");
            NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
            List<Instance> instances = namingService.selectInstances(name, group, true);
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
                    log.warn(
                            "A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
                            name, clusterName, instances);
                }
            }
            if (LoginUtil.getVersion().isPresent()) {
                String appVersion = LoginUtil.getVersion().get();
                List<Instance> versionMatchInstances = instancesToChoose.parallelStream()
                        .filter(instance -> appVersion.equals(instance.getMetadata().get(AppConstants.VERSION_KEY)))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(versionMatchInstances)) {
                    instancesToChoose = versionMatchInstances;
                } else {
                    log.warn("Can not find version={} match server", appVersion);
                }
            }
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);

            return new NacosServer(instance);
        } catch (Exception e) {
            log.warn("NacosRule error", e);
            return null;
        }
    }

    private boolean isBlockServer(String tergetServerName) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 判断目标服务是否为console服务
        if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, tergetServerName)) {
            // 判断当前服务是否为console 或 gateway 服务，都不是则进制调用
            if (antPathMatcher.match(CONSOLE_SERVER_NAME_PATTERN, versionProperties.getRegisterServerName())
                    || antPathMatcher.match(GATEWAY_SERVER_NAME_PATTERN, versionProperties.getRegisterServerName())) {
                return true;
            }
        }
        return false;
    }
}
