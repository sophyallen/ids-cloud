package com.kkb.idscloud.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kkb.idscloud.base.model.entity.GatewayRoute;
import com.kkb.idscloud.common.model.PageParams;
import com.kkb.idscloud.common.mybatis.base.service.IBaseService;

import java.util.List;

/**
 * 路由管理
 *
 * @author zmc
 */
public interface GatewayRouteService extends IBaseService<GatewayRoute> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<GatewayRoute> findListPage(PageParams pageParams);

    /**
     * 查询可用路由列表
     *
     * @return
     */
    List<GatewayRoute> findRouteList();

    /**
     * 获取路由信息
     *
     * @param routeId
     * @return
     */
    GatewayRoute getRoute(Long routeId);

    /**
     * 添加路由
     *
     * @param route
     */
    void addRoute(GatewayRoute route);

    /**
     * 更新路由
     *
     * @param route
     */
    void updateRoute(GatewayRoute route);

    /**
     * 删除路由
     *
     * @param routeId
     */
    void removeRoute(Long routeId);

    /**
     * 是否存在
     *
     * @param routeName
     * @return
     */
    Boolean isExist(String routeName);
}
