package com.kaikeba.idscloud.flux.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shenjp
 * @description 认证配置类
 * @date 2020/12/26
 */
@ConfigurationProperties(prefix = "auth.corgi")
@Data
public class AuthCorgiProperties {
    private String tenantId;
    private String appId;
    private String appSecret;
    private String baseUrl;
    private String checkTokenUrl;
    private String permissionListUrl;

}
