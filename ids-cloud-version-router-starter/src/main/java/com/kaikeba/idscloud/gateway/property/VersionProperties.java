package com.kaikeba.idscloud.gateway.property;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.kaikeba.idscloud.common.core.constants.AppConstants;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@Data
@ConfigurationProperties(prefix = "ids.cloud.version")
public class VersionProperties {
    private boolean enable;
    private String grayVersion;
    private String grayGroup;
    private boolean checkCallable = true;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    public String getServerVersion() {
        return nacosDiscoveryProperties.getMetadata().getOrDefault(AppConstants.VERSION_KEY,
                AppConstants.DEFAULT_SERVER_VERSION);
    }

    public AppConstants.ServerTypeEnum getServerType() {
        String serverTypeStr = nacosDiscoveryProperties.getMetadata().get(AppConstants.SERVER_TYPE_KEY);
        return Optional.ofNullable(serverTypeStr)
                .map(AppConstants.ServerTypeEnum::valueOf)
                .orElse(AppConstants.ServerTypeEnum.SERVER_TOC);
    }

    public String getServerName() {
        return System.getProperties().getProperty(AppConstants.SERVER_NAME_PROPERTITY_KEY, AppConstants.DEFAULT_SERVER_NAME);
    }

}
