package com.kkb.idscloud.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.idscloud.common.mybatis.base.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zmc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("gateway_rate_limit_api")
public class GatewayRateLimitApi extends AbstractEntity {
    /**
     * 限制数量
     */
    private Long policyId;

    /**
     * 时间间隔(秒)
     */
    private Long apiId;


    private static final long serialVersionUID = 1L;
}
