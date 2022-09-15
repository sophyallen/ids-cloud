package com.ids.cloud.flux.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 * 网关属性配置类
 *
 * @author zmc
 * @date: 2018/11/23 14:40
 * @description:
 */
@Data
@ConfigurationProperties(prefix = "ids.api")
public class ApiProperties {
    /**
     * 是否开启签名验证
     */
    private Boolean checkSign = true;
    /**
     * 是否开启动态访问控制
     */
    private Boolean accessControl = true;

    /**
     * 是否开启接口调试
     */
    private Boolean apiDebug = false;

    /**
     * 签名忽略请求
     */
    private Set<String> signIgnores;

    /**
     * 始终放行，无需登录鉴权
     */
    private Set<String> allowUrls = new HashSet<>();

    /**
     * 禁用接口
     */
    private Set<String> blockUrls = new HashSet<>();

    /**
     * 接口鉴权对应关系
     */
    private Map<String, List<String>> urlPermissionMapping = new HashMap<>();
}
