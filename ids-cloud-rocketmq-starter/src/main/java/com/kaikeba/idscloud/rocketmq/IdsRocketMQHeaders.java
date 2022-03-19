package com.kaikeba.idscloud.rocketmq;

import org.apache.rocketmq.spring.support.RocketMQHeaders;

/**
 * @author: zmc
 * @date: 3/19/22 11:43
 * @description:
 */
public class IdsRocketMQHeaders extends RocketMQHeaders {
    /**
     * 兼容ONS延时消息
     * 消息开始投放时间
     */
    public static final String START_DELIVER_TIME = "__STARTDELIVERTIME";
}
