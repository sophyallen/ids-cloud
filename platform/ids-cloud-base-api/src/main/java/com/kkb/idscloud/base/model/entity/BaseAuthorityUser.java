package com.kkb.idscloud.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.idscloud.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统权限-用户关联
 * @author zmc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("base_authority_user")
public class BaseAuthorityUser extends AbstractEntity {
    /**
     * 权限ID
     */
    private Long authorityId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 过期时间
     */
    private Date expireTime;

    private static final long serialVersionUID = 1L;
}
