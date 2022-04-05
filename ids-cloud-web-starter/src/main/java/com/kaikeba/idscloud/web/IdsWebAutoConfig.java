package com.kaikeba.idscloud.web;

import com.kaikeba.idscloud.web.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author: zhangminchao
 * @date: 2022/04/02
 * @description:
 */
@Slf4j
@EnableSwagger2WebMvc
@ConditionalOnWebApplication
public class IdsWebAutoConfig {
    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler exceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        log.info("OpenGlobalExceptionHandler [{}]", exceptionHandler);
        return exceptionHandler;
    }

    @Bean
    @ConditionalOnMissingBean(WebMvcConfiguration.class)
    public WebMvcConfigurer getWebMvcConfiguration() {
        return new WebMvcConfiguration();
    }
}
