package com.kaikeba.idscloud.flux.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author: zhangminchao
 * @date: 2022/04/08
 * @description:
 */
@ConfigurationProperties(prefix = "auth.passport")
@Data
@Slf4j
public class AuthPassportProperties {
    @Deprecated
    private String group;
    @Deprecated
    private String appId;
    private String baseUrl;
    private String userInfoUrl;
    private String defaultGroup;
    private Map<String, GroupProperties> groups;

    @Data
    public static class GroupProperties {
        private String appId;
        private String group;
    }
    public GroupProperties getGroupByHostName(String hostName) {
        String groupName = getGroupNameFromHostName(hostName);
        log.debug("get group name from hostname[{}] is : {}", hostName, groupName);
        if (StringUtils.isBlank(groupName) || !groups.containsKey(groupName)) {
            return groups.get(defaultGroup);
        }
        return groups.get(groupName);
    }
    public String getGroupNameFromHostName(String hostName) {
        if (StringUtils.isBlank(hostName)) {
            return null;
        }
        String[] splits = hostName.split("\\.");
        if (splits.length <= 2) {
            return null;
        }
        String groupName = splits[splits.length-2];
        return groupName;
    }
}
