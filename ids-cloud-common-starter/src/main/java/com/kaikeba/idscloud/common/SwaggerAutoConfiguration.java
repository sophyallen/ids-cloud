package com.kaikeba.idscloud.common;

import com.kaikeba.idscloud.common.swagger.IdsSwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger文档生成配置
 *
 * @author zmc
 */
@Slf4j
@EnableConfigurationProperties({IdsSwaggerProperties.class})
@ConditionalOnProperty(prefix = "idscloud.swagger2", name = "enabled", havingValue = "true")
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {
    private IdsSwaggerProperties idsSwaggerProperties;

    public SwaggerAutoConfiguration(IdsSwaggerProperties idsSwaggerProperties) {
        this.idsSwaggerProperties = idsSwaggerProperties;
        log.info("SwaggerProperties [{}]", idsSwaggerProperties);
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(idsSwaggerProperties.isEnabled())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kaikeba"))
                .paths(PathSelectors.any())
                .build()
                .host(idsSwaggerProperties.getHost())
                .globalOperationParameters(parameters());
    }

    /**
     * 构建全局参数
     * 这里主要针对网关服务外部访问数字验签所需参数
     * 只在网关服务开启{idscloud.resource-server.enabled-validate-sign=true}时生效.
     * 未开启,可以不填写
     *
     * @return
     */
    private List<Parameter> parameters() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        builder.name("Authorization").description("公共参数: 认证token, B端： Bearer xxx; C端：Bearer pc|mobile:xxx")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true);
        pars.add(builder.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(idsSwaggerProperties.getTitle())
                .description(idsSwaggerProperties.getDescription())
                .version("2.0")
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(UiConfiguration.class)
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }


}
