package com.kaikeba.idscloud.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zmc
 */
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final RouteDefinitionLocator routeDefinitionLocator;

    public SwaggerProvider(RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        routeDefinitionLocator.getRouteDefinitions()
                .filter(routeDefinition -> routeDefinition.getUri().toString().contains("lb://"))
                .subscribe(routeDefinition -> {
                            routeDefinition.getPredicates().stream()
                                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                                    .filter(predicateDefinition -> !predicateDefinition.getArgs().containsKey("_rateLimit"))
                                    .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                            predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                                    .replace("**", "v2/api-docs"))));
                        }
                );
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}