package com.kaikeba.idscloud.common;

import com.kaikeba.idscloud.common.interceptor.FeignRequestInterceptor;
import com.kaikeba.idscloud.common.utils.FeignLogger;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign OAuth2 request interceptor.
 *
 * @author zmc
 */
@Slf4j
@Configuration
public class FeignAutoConfiguration {

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        Encoder encoder = new FeignSpringFormEncoder(new SpringEncoder(messageConverters));
        log.info("FeignSpringFormEncoder [{}]", encoder);
        return encoder;
    }

//    @Bean
//    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        FeignRequestInterceptor interceptor = new FeignRequestInterceptor();
        log.info("FeignRequestInterceptor [{}]", interceptor);
        return interceptor;
    }

}
