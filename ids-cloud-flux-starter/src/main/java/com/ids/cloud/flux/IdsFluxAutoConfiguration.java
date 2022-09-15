package com.ids.cloud.flux;

import com.ids.cloud.common.utils.SpringContextHolder;
import com.ids.cloud.flux.configuration.ApiProperties;
import com.ids.cloud.flux.configuration.AuthCorgiProperties;
import com.ids.cloud.flux.configuration.AuthPassportProperties;
import com.ids.cloud.flux.exception.JsonExceptionHandler;
import com.ids.cloud.flux.filter.AccessLogFilter;
import com.ids.cloud.flux.filter.GatewayContextFilter;
import com.ids.cloud.flux.filter.RemoveGatewayContextFilter;
import com.ids.cloud.flux.service.AccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

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
public class IdsFluxAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        log.info("SpringContextHolder [{}]", holder);
        return holder;
    }

    @Bean
    @ConditionalOnMissingBean(AccessLogService.class)
    public AccessLogService accessLogService() {
        return new AccessLogService();
    }

    @Bean
    @ConditionalOnMissingBean(AccessLogFilter.class)
    public AccessLogFilter accessLogFilter(AccessLogService accessLogService) {
        return new AccessLogFilter(accessLogService);
    }
    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider
     * @param serverCodecConfigurer
     * @return
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer, AccessLogService accessLogService) {

        JsonExceptionHandler jsonExceptionHandler = new JsonExceptionHandler(accessLogService);
        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        log.info("ErrorWebExceptionHandler [{}]", jsonExceptionHandler);
        return jsonExceptionHandler;
    }

    @Bean
    @ConditionalOnMissingBean(GatewayContextFilter.class)
    public GatewayContextFilter gatewayContextFilter(){
        log.debug("Load GatewayContextFilter Config Bean");
        return new GatewayContextFilter();
    }

    @Bean
    @ConditionalOnMissingBean(RemoveGatewayContextFilter.class)
    public RemoveGatewayContextFilter removeGatewayContextFilter(){
        RemoveGatewayContextFilter gatewayContextFilter = new RemoveGatewayContextFilter();
        log.debug("Load RemoveGatewayContextFilter Config Bean");
        return gatewayContextFilter;
    }

     /*@Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }*/

  /*  @Bean
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }*/
}
