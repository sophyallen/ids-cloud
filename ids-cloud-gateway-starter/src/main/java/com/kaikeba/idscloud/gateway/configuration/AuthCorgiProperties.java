package com.kaikeba.idscloud.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shenjp
 * @description 认证配置类
 * @date 2020/12/26
 */
@ConfigurationProperties(prefix = "auth.corgi")
@Component
@Data
public class AuthCorgiProperties {
    private String tenantId;
    private String appId;
    private String appSecret;
    private String baseUrl;
    private String checkTokenUrl;
    private String permissionListUrl;

}
