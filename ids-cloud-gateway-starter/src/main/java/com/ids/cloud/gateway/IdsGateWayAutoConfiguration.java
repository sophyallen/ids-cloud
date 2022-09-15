package com.ids.cloud.gateway;

import com.ids.cloud.common.utils.SpringContextHolder;
import com.ids.cloud.flux.configuration.ApiProperties;
import com.ids.cloud.flux.configuration.AuthCorgiProperties;
import com.ids.cloud.flux.configuration.AuthPassportProperties;
import com.ids.cloud.gateway.configuration.SwaggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
@EnableConfigurationProperties(value = {ApiProperties.class, AuthCorgiProperties.class, AuthPassportProperties.class})
public class IdsGateWayAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        log.info("SpringContextHolder [{}]", holder);
        return holder;
    }

    @Bean
    @ConditionalOnBean(RouteDefinitionLocator.class)
    public SwaggerProvider swaggerProvider(RouteDefinitionLocator routeDefinitionLocator) {
        return new SwaggerProvider(routeDefinitionLocator);
    }

}
