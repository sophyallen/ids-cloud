package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.AuthorityResource;
import com.kkb.idscloud.base.model.AuthorityAction;
import com.kkb.idscloud.base.model.AuthorityApi;
import com.kkb.idscloud.base.model.AuthorityMenu;
import com.kkb.idscloud.base.model.entity.BaseAuthority;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import com.kkb.idscloud.common.security.OpenAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zmc
 */
@Repository
public interface BaseAuthorityMapper extends SuperMapper<BaseAuthority> {

    /**
     * 查询所有资源授权列表
     * @return
     */
    List<AuthorityResource> selectAllAuthorityResource();

    /**
     * 查询已授权权限列表
     *
     * @param map
     * @return
     */
    List<OpenAuthority> selectAuthorityAll(Map map);


    /**
     * 获取菜单权限
     *
     * @param map
     * @return
     */
    List<AuthorityMenu> selectAuthorityMenu(Map map);

    /**
     * 获取操作权限
     *
     * @param map
     * @return
     */
    List<AuthorityAction> selectAuthorityAction(Map map);

    /**
     * 获取API权限
     *
     * @param map
     * @return
     */
    List<AuthorityApi> selectAuthorityApi(Map map);


}
