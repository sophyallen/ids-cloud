package com.kaikeba.idscloud.flux;

import com.kaikeba.idscloud.common.core.annotation.Condition;
import com.kaikeba.idscloud.common.utils.SpringContextHolder;
import com.kaikeba.idscloud.flux.configuration.ApiProperties;
import com.kaikeba.idscloud.flux.configuration.AuthCorgiProperties;
import com.kaikeba.idscloud.flux.configuration.AuthPassportProperties;
import com.kaikeba.idscloud.flux.exception.JsonExceptionHandler;
import com.kaikeba.idscloud.flux.filter.AccessLogFilter;
import com.kaikeba.idscloud.flux.filter.GatewayContextFilter;
import com.kaikeba.idscloud.flux.filter.RemoveGatewayContextFilter;
import com.kaikeba.idscloud.flux.service.AccessLogService;
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
     * ?????????????????????[@@]??????Bean????????????Bean?????????????????????????????????????????????????????????
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
