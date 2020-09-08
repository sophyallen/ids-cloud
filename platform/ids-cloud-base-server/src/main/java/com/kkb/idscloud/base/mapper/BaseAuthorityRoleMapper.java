package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.AuthorityMenu;
import com.kkb.idscloud.base.model.entity.BaseAuthorityRole;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import com.kkb.idscloud.common.security.OpenAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zmc
 */
@Repository
public interface BaseAuthorityRoleMapper extends SuperMapper<BaseAuthorityRole> {

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    List<OpenAuthority> selectAuthorityByRole(@Param("roleId") Long roleId);

    /**
     * 获取角色菜单权限
     *
     * @param roleId
     * @return
     */
    List<AuthorityMenu> selectAuthorityMenuByRole(@Param("roleId") Long roleId);
}
