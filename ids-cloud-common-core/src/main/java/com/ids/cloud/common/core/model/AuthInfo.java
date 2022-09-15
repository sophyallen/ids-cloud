package com.ids.cloud.common.core.model;

import com.ids.cloud.common.core.constants.AppConstants;
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
    /**
     * 授权平台： PASSPORT、CORGI
      */
    private AppConstants.AuthPlatformEnum platform;
    /**
     * C端用户ID, corgi登录可能为空
     */
    private Long userId;
    /**
     * corgi用户ID
     */
    private Long corgiUserId;
    /**
     * Authorization toekn
     */
    private String token;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户真实姓名
     */
    private String name;
}
