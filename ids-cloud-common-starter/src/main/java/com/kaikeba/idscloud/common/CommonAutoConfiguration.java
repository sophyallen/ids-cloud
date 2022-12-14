package com.kaikeba.idscloud.common;

import com.kaikeba.idscloud.common.configuration.IdsCommonProperties;
import com.kaikeba.idscloud.common.configuration.IdsScanProperties;
import com.kaikeba.idscloud.common.configuration.SnowflakeProperties;
import com.kaikeba.idscloud.common.filter.XFilter;
import com.kaikeba.idscloud.common.core.gen.SnowflakeIdGenerator;
import com.kaikeba.idscloud.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * 默认配置类
 *
 * @author zmc
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({IdsCommonProperties.class, SnowflakeProperties.class,  IdsScanProperties.class})
public class CommonAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(IdsScanProperties.class)
     public IdsScanProperties scanProperties(){
         return  new IdsScanProperties();
     }

    /**
     * 默认加密配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(BCryptPasswordEncoder.class)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("BCryptPasswordEncoder [{}]", encoder);
        return encoder;
    }


    /**
     * Spring上下文工具配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        log.info("SpringContextHolder [{}]", holder);
        return holder;
    }

    /**
     * ID生成器配置
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SnowflakeProperties.class)
    public SnowflakeIdGenerator snowflakeIdWorker(SnowflakeProperties properties) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(properties.getWorkId(), properties.getCenterId());
        log.info("SnowflakeIdGenerator [{}]", snowflakeIdGenerator);
        return snowflakeIdGenerator;
    }


    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        log.info("RestTemplate [{}]", restTemplate);
        return restTemplate;
    }

    /**
     * xss过滤
     * body缓存
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean XssFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new XFilter());
        log.info("XFilter [{}]", filterRegistrationBean);
        return filterRegistrationBean;
    }

}
