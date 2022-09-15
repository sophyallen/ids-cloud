package com.ids.cloud.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangminchao
 * @date: 2022/04/14
 * @description: 放入TraceContext版本对象，转化趁JSON后长度不能超过128
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VersionInfo implements Serializable {
    /**
     * 请求的版本信息
     */
    private String version;
    /**
     * 灰度服务注册的服务组
     * 默认：GRAY_GROUP
     */
    private String grayGroup;
    /**
     * 是否为灰度流量
     */
    private boolean isGray = false;
}
