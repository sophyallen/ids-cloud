package com.kkb.idscloud.common;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.kkb.idscloud.common.swagger.IdsSwaggerProperties;
import com.kkb.idscloud.common.core.utils.DateUtils;
import com.kkb.idscloud.common.core.utils.RandomValueUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Swagger文档生成配置
 *
 * @author zmc
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({IdsSwaggerProperties.class})
@ConditionalOnProperty(prefix = "idscloud.swagger2", name = "enabled", havingValue = "true")
@Import({Swagger2DocumentationConfiguration.class})
public class SwaggerAutoConfiguration {
    private IdsSwaggerProperties idsSwaggerProperties;
    private static final String SCOPE_PREFIX = "scope.";
    private Locale locale = LocaleContextHolder.getLocale();
    private MessageSource messageSource;

    public SwaggerAutoConfiguration(IdsSwaggerProperties idsSwaggerProperties, MessageSource messageSource) {
        this.idsSwaggerProperties = idsSwaggerProperties;
        this.messageSource = messageSource;
        log.info("SwaggerProperties [{}]", idsSwaggerProperties);
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(idsSwaggerProperties.isEnabled())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
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
        builder.name("Authorization").description("公共参数: 认证token, C端： Bearer xxx, B端：Bearer pc|mobile:xxx")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true);
        pars.add(builder.build());
        builder = new ParameterBuilder();
        builder.name("appId").description("公共参数: corgi appId, B端必填")
                .allowableValues(new AllowableListValues(Lists.newArrayList("12358", "12350"), "string"))
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true);
        pars.add(builder.build());
//        builder = new ParameterBuilder();
//        builder.name("tenantId").description("公共参数: corgi 租户id, B段必填")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .defaultValue("6XWFVymtaB68REyRBuf")
//                .required(true);
//        pars.add(builder.build());
//        builder = new ParameterBuilder();
//        builder.name("Cookie").description("公共参数: 学习中心上课平台, C端必填")
//                .allowableValues(new AllowableListValues(Lists.newArrayList("passport_platform=pc;",
//                        "passport_platform=mobile;"),
//                        "string"))
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(true);
//        pars.add(builder.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(idsSwaggerProperties.getTitle())
                .description(idsSwaggerProperties.getDescription())
                .version("1.0")
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }


}
