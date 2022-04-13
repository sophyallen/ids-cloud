package com.kaikeba.idscloud.gateway;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.kaikeba.idscloud.gateway.property.VersionProperties;
import com.kaikeba.idscloud.gateway.rule.VersionRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@ConditionalOnNacosDiscoveryEnabled
@Import(VersionProperties.class)
public class VersionRuleAutoConfiguration {

    @Bean
    IRule getVersionRule() {
        return new VersionRule();
    }
}
