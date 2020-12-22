package com.kkb.idscloud.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源扫描配置
 *
 * @author zmc
 * @date 2018/7/29
 */
@Data
@ConfigurationProperties(prefix = "idscloud.scan")
public class IdsScanProperties {

    /**
     * 请求资源注册到API列表
     */
    private Boolean registerRequestMapping = true;

    public boolean isRegisterRequestMapping() {
        return registerRequestMapping;
    }

    public void setRegisterRequestMapping(boolean registerRequestMapping) {
        this.registerRequestMapping = registerRequestMapping;
    }


}
