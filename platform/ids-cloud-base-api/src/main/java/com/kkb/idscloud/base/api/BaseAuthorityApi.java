package com.kkb.idscloud.base.api;

import com.kkb.idscloud.base.constants.BaseConstants;
import com.kkb.idscloud.base.model.AuthorityMenu;
import com.kkb.idscloud.base.model.AuthorityResource;
import com.kkb.idscloud.common.model.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 权限控制API接口
 *
 * @author zmc
 */
@FeignClient(value = BaseConstants.BASE_SERVER)
public interface BaseAuthorityApi {
    /**
     * 获取所有访问权限列表
     * @return
     */
    @GetMapping("/authority/access")
    ResultBody<List<AuthorityResource>> findAuthorityResource();

    /**
     * 获取菜单权限列表
     *
     * @return
     */
    @GetMapping("/authority/menu")
    ResultBody<List<AuthorityMenu>> findAuthorityMenu();
}
