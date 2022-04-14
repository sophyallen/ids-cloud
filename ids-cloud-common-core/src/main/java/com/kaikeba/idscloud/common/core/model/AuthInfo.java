package com.kaikeba.idscloud.common.core.model;

import com.kaikeba.idscloud.common.core.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangminchao
 * @date: 2022/04/14
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo implements Serializable {
    private AppConstants.AuthPlatformEnum platform;
    private Long userId;
    private Long corgiUserId;
    private String token;
    private String nickname;
    private String name;
}
