package com.kkb.idscloud.base.api;

import com.kkb.idscloud.base.constants.BaseConstants;
import com.kkb.idscloud.base.model.entity.BaseApp;
import com.kkb.idscloud.common.model.ResultBody;
import com.kkb.idscloud.common.security.OpenClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zmc
 */
@FeignClient(value = BaseConstants.BASE_SERVER)
public interface BaseAppApi {

    /**
     * 获取应用基础信息
     *
     * @param appId 应用Id
     * @return
     */
    @GetMapping("/app/{appId}/info")
    ResultBody<BaseApp> getApp(@PathVariable("appId") String appId);

    /**
     * 获取应用开发配置信息
     * @param clientId
     * @return
     */
    @GetMapping("/app/client/{clientId}/info")
    ResultBody<OpenClientDetails> getAppClientInfo(@PathVariable("clientId") String clientId);
}
