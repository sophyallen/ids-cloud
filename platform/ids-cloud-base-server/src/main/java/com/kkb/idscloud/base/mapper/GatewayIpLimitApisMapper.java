package com.kkb.idscloud.base.mapper;

import com.kkb.idscloud.base.model.IpLimitApi;
import com.kkb.idscloud.common.mybatis.base.mapper.SuperMapper;
import com.kkb.idscloud.base.model.entity.GatewayIpLimitApi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zmc
 */
@Repository
public interface GatewayIpLimitApisMapper extends SuperMapper<GatewayIpLimitApi> {

    List<IpLimitApi> selectIpLimitApi(@Param("policyType") int policyType);
}
