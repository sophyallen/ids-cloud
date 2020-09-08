package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.entity.BaseRole;
import com.kkb.idscloud.base.model.entity.BaseRoleUser;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zmc
 */
@Repository
public interface BaseRoleUserMapper extends SuperMapper<BaseRoleUser> {
    /**
     * 查询系统用户角色
     *
     * @param userId
     * @return
     */
    List<BaseRole> selectRoleUserList(@Param("userId") Long userId);

    /**
     * 查询用户角色ID列表
     * @param userId
     * @return
     */
    List<Long> selectRoleUserIdList(@Param("userId") Long userId);
}
