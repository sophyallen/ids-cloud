package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.entity.GatewayAccessLogs;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

/**
 * @author zmc
 */
@Repository
public interface GatewayLogsMapper extends SuperMapper<GatewayAccessLogs> {
}
