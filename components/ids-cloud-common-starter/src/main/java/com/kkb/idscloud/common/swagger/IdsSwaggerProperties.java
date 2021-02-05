package com.kkb.idscloud.common.swagger;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 自定义swagger配置
 *
 * @author zmc
 * @date 2018/7/29
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "idscloud.swagger2")
public class IdsSwaggerProperties {
    /**
     * 是否启用swagger,生产环境建议关闭
     */
    private boolean enabled;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 接口请求地址，默认Request获取，由于网关rewrite为127.0.0.1
     * 服务器上获取不到真正域名
     */
    private String host;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 获取token
     */
    private String accessTokenUri;
    /**
     * 认证地址
     */
    private String userAuthorizationUri;

    private List<String> ignores = Lists.newArrayList();

}
