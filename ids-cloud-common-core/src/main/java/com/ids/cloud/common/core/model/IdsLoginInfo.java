package com.ids.cloud.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdsLoginInfo implements Serializable {
    private Long loginUserId;
    private String loginFlatForm;
    private String userName;
    private String version;
}
