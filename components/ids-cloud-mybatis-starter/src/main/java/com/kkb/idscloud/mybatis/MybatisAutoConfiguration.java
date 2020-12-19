package com.kkb.idscloud.mybatis;

/**
 * 默认配置类
 *
 * @author zmc
 */
@Slf4j
@Configuration
@EnableConfigurationProperties
public class MybatisAutoConfiguration {
    /**
     * 自动填充模型数据
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ModelMetaObjectHandler.class)
    public ModelMetaObjectHandler modelMetaObjectHandler() {
        ModelMetaObjectHandler metaObjectHandler = new ModelMetaObjectHandler();
        log.info("ModelMetaObjectHandler [{}]", metaObjectHandler);
        return metaObjectHandler;
    }
}
