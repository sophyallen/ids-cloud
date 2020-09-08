package com.kkb.idscloud.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kkb.idscloud.base.model.entity.BaseMenu;
import com.kkb.idscloud.common.model.PageParams;
import com.kkb.idscloud.common.mybatis.base.service.IBaseService;

import java.util.List;

/**
 * 菜单资源管理
 * @author zmc
 */
public interface BaseMenuService extends IBaseService<BaseMenu> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    IPage<BaseMenu> findListPage(PageParams pageParams);

    /**
     * 查询列表
     * @return
     */
    List<BaseMenu> findAllList();

    /**
     * 根据主键获取菜单
     *
     * @param menuId
     * @return
     */
    BaseMenu getMenu(Long menuId);

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    Boolean isExist(String menuCode);


    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    BaseMenu addMenu(BaseMenu menu);

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    BaseMenu updateMenu(BaseMenu menu);

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    void removeMenu(Long menuId);
}
