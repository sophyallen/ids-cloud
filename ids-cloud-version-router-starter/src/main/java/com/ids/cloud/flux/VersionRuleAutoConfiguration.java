package com.ids.cloud.flux;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.ids.cloud.flux.rule.VersionRule;
import com.ids.cloud.flux.property.VersionProperties;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@ConditionalOnNacosDiscoveryEnabled
@EnableConfigurationProperties(VersionProperties.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class VersionRuleAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "ids.cloud.version", name = "enable",
            havingValue = "true", matchIfMissing = false)
    @Scope(SCOPE_PROTOTYPE)
    public IRule ribbonRule() {
        VersionRule rule = new VersionRule();
        return rule;
    }
}
