package com.kkb.idscloud.portal.uaa.service.feign;

import com.kkb.idscloud.base.constants.BaseConstants;
import com.kkb.idscloud.base.api.BaseDeveloperApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: zmc
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = BaseConstants.BASE_SERVER)
public interface BaseDeveloperClient extends BaseDeveloperApi {


}
