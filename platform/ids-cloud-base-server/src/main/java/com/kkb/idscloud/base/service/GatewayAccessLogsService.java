package com.kkb.idscloud.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kkb.idscloud.base.model.entity.GatewayAccessLogs;
import com.kkb.idscloud.common.model.PageParams;

/**
 * 网关访问日志
 * @author zmc
 */
public interface GatewayAccessLogsService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<GatewayAccessLogs> findListPage(PageParams pageParams);
}
