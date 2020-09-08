package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.AuthorityMenu;
import com.kkb.idscloud.base.model.entity.BaseAuthorityUser;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import com.kkb.idscloud.common.security.OpenAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zmc
 */
@Repository
public interface BaseAuthorityUserMapper extends SuperMapper<BaseAuthorityUser> {

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @return
     */
    List<OpenAuthority> selectAuthorityByUser(@Param("userId") Long userId);

    /**
     * 获取用户已授权权限完整信息
     *
     * @param userId
     * @return
     */
    List<AuthorityMenu> selectAuthorityMenuByUser(@Param("userId") Long userId);
}
