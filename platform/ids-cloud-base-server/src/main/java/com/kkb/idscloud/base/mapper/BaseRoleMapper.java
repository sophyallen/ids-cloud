package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.entity.BaseRole;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zmc
 */
@Repository
public interface BaseRoleMapper extends SuperMapper<BaseRole> {

    List<BaseRole> selectRoleList(Map params);
}
