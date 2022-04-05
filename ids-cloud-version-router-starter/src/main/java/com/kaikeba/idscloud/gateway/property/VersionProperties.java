package com.kaikeba.idscloud.gateway.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@Data
@Configuration
public class VersionProperties {
    @Value("${spring.application.name}")
    private String registerServerName;
    @Value("${spring.cloud.nacos.discovery.metadata.version}")
    private String registerServerVersion;
}
