package com.kaikeba.idscloud.common.core.model;

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
    private String grayGroup;
    private boolean isGray = false;
}
