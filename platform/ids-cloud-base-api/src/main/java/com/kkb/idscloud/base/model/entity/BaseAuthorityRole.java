package com.kkb.idscloud.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.idscloud.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统权限-角色关联
 * @author zmc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("base_authority_role")
public class BaseAuthorityRole extends AbstractEntity {
    /**
     * 权限ID
     */
    private Long authorityId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 过期时间:null表示长期
     */
    private Date expireTime;

    private static final long serialVersionUID = 1L;
}
