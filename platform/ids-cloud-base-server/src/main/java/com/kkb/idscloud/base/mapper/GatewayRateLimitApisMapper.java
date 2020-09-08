package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.RateLimitApi;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import com.kkb.idscloud.base.model.entity.GatewayRateLimitApi;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zmc
 */
@Repository
public interface GatewayRateLimitApisMapper extends SuperMapper<GatewayRateLimitApi> {

    List<RateLimitApi> selectRateLimitApi();

}
