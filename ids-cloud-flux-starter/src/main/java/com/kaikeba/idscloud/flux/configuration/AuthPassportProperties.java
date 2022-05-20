package com.kaikeba.idscloud.flux.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: zhangminchao
 * @date: 2022/04/08
 * @description:
 */
@ConfigurationProperties(prefix = "auth.passport")
@Data
public class AuthPassportProperties {
    private String group;
    private String appId;
    private String baseUrl;
    private String userInfoUrl;
}
