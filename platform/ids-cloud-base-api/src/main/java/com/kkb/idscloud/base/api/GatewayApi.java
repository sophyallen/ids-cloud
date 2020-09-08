package com.kkb.idscloud.base.api;

import com.kkb.idscloud.base.constants.BaseConstants;
import com.kkb.idscloud.base.model.IpLimitApi;
import com.kkb.idscloud.base.model.RateLimitApi;
import com.kkb.idscloud.base.model.entity.GatewayRoute;
import com.kkb.idscloud.common.model.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zmc
 */
@FeignClient(value = BaseConstants.BASE_SERVER)
public interface GatewayApi {

    /**
     * 获取接口黑名单列表
     *
     * @return
     */
    @GetMapping("/gateway/api/blackList")
     ResultBody<List<IpLimitApi>> getApiBlackList() ;

    /**
     * 获取接口白名单列表
     * @return
     */
    @GetMapping("/gateway/api/whiteList")
    ResultBody<List<IpLimitApi> > getApiWhiteList();

    /**
     * 获取限流列表
     * @return
     */
    @GetMapping("/gateway/api/rateLimit")
    ResultBody<List<RateLimitApi> > getApiRateLimitList();

    /**
     * 获取路由列表
     * @return
     */
    @GetMapping("/gateway/api/route")
    ResultBody<List<GatewayRoute> > getApiRouteList();
}
