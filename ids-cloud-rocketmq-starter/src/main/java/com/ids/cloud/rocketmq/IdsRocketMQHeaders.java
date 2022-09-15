package com.ids.cloud.rocketmq;


/**
 * @author: zmc
 * @date: 3/19/22 11:43
 * @description:
 */
public class IdsRocketMQHeaders {
    /**
     * 兼容ONS延时消息
     * 消息开始投放时间
     */
    public static final String START_DELIVER_TIME = "__STARTDELIVERTIME";
    public static final String PREFIX = "rocketmq_";
    public static final String KEYS = "KEYS";
    public static final String TAGS = "TAGS";
    public static final String TOPIC = "TOPIC";
    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String BORN_TIMESTAMP = "BORN_TIMESTAMP";
    public static final String BORN_HOST = "BORN_HOST";
    public static final String FLAG = "FLAG";
    public static final String QUEUE_ID = "QUEUE_ID";
    public static final String SYS_FLAG = "SYS_FLAG";
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
}
