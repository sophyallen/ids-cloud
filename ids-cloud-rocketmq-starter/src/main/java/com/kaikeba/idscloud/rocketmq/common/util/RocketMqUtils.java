package com.kaikeba.idscloud.rocketmq.common.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class RocketMqUtils {
    public static final String TIMING_HEADER = "__STARTDELIVERTIME";


    /**
     * 发送定时消息
     *
     * @param t         消息内容
     * @param timestamp 送达时间的时间戳
     * @return 消息结构
     */
    public static <T> Message<T> sendTimingMsg(T t, Long timestamp) {
        return MessageBuilder.withPayload(t).setHeader(TIMING_HEADER, timestamp).build();
    }


    /**
     * 发送消息
     *
     * @param t 消息内容
     * @return 消息结构
     */
    public static <T> Message<T> sendMsg(T t) {
        return MessageBuilder.withPayload(t).build();
    }
}