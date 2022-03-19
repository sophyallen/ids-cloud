package com.kaikeba.idscloud.rocketmq;


import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: zmc
 * @date: 3/19/22 11:08
 * @description:
 */
public class RocketMqMessageBuilder<T> {
    
    private MessageBuilder<T> messageBuilder;

    private RocketMqMessageBuilder(MessageBuilder<T> messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    /**
     * 设置定时消息
     * @param timestamp
     * @return
     */
    public RocketMqMessageBuilder<T> setStartDeliverTime(long timestamp) {
        messageBuilder.setHeader(IdsRocketMQHeaders.START_DELIVER_TIME, timestamp);
        return this;
    }

    public RocketMqMessageBuilder<T> setDelayDuring(long during, TimeUnit timeUnit) {
        messageBuilder.setHeader(IdsRocketMQHeaders.START_DELIVER_TIME, System.currentTimeMillis() + timeUnit.toMillis(during));
        return this;
    }

    public RocketMqMessageBuilder<T> setTags(String tags) {
        ErrorCodeEnum.CLIENT_ERROR_A0410.assertNotEmpty(tags);
        messageBuilder.setHeader(IdsRocketMQHeaders.TAGS, tags);
        return this;
    }

    /**
     * Set the message headers to use by providing a {@code MessageHeaderAccessor}.
     * @param accessor the headers to use
     */
    public RocketMqMessageBuilder<T> setHeaders(MessageHeaderAccessor accessor) {
        Assert.notNull(accessor, "MessageHeaderAccessor must not be null");
        messageBuilder.setHeaders(accessor);
        return this;
    }

    /**
     * Set the value for the given header name. If the provided value is {@code null},
     * the header will be removed.
     */
    public RocketMqMessageBuilder<T> setHeader(String headerName, @Nullable Object headerValue) {
        this.messageBuilder.setHeader(headerName, headerValue);
        return this;
    }

    /**
     * Set the value for the given header name only if the header name is not already
     * associated with a value.
     */
    public RocketMqMessageBuilder<T> setHeaderIfAbsent(String headerName, Object headerValue) {
        this.messageBuilder.setHeaderIfAbsent(headerName, headerValue);
        return this;
    }

    /**
     * Removes all headers provided via array of 'headerPatterns'. As the name suggests
     * the array may contain simple matching patterns for header names. Supported pattern
     * styles are: "xxx*", "*xxx", "*xxx*" and "xxx*yyy".
     */
    public RocketMqMessageBuilder<T> removeHeaders(String... headerPatterns) {
        this.messageBuilder.removeHeaders(headerPatterns);
        return this;
    }
    /**
     * Remove the value for the given header name.
     */
    public RocketMqMessageBuilder<T> removeHeader(String headerName) {
        this.messageBuilder.removeHeader(headerName);
        return this;
    }

    /**
     * Copy the name-value pairs from the provided Map. This operation will overwrite any
     * existing values. Use { {@link #copyHeadersIfAbsent(Map)} to avoid overwriting
     * values. Note that the 'id' and 'timestamp' header values will never be overwritten.
     */
    public RocketMqMessageBuilder<T> copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        this.messageBuilder.copyHeaders(headersToCopy);
        return this;
    }

    /**
     * Copy the name-value pairs from the provided Map. This operation will <em>not</em>
     * overwrite any existing values.
     */
    public RocketMqMessageBuilder<T> copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        this.messageBuilder.copyHeadersIfAbsent(headersToCopy);
        return this;
    }

    public RocketMqMessageBuilder<T> setReplyChannel(MessageChannel replyChannel) {
        this.messageBuilder.setReplyChannel(replyChannel);
        return this;
    }

    public RocketMqMessageBuilder<T> setReplyChannelName(String replyChannelName) {
        this.messageBuilder.setReplyChannelName(replyChannelName);
        return this;
    }

    public RocketMqMessageBuilder<T> setErrorChannel(MessageChannel errorChannel) {
        this.messageBuilder.setErrorChannel(errorChannel);
        return this;
    }

    public RocketMqMessageBuilder<T> setErrorChannelName(String errorChannelName) {
        this.messageBuilder.setErrorChannelName(errorChannelName);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Message<T> build() {
        return messageBuilder.build();
    }


    /**
     * Create a builder for a new {@link Message} instance pre-populated with all of the
     * headers copied from the provided message. The payload of the provided Message will
     * also be used as the payload for the new message.
     * @param message the Message from which the payload and all headers will be copied
     */
    public static <T> RocketMqMessageBuilder<T> fromMessage(Message<T> message) {
        
        return new RocketMqMessageBuilder(MessageBuilder.fromMessage(message));
    }

    /**
     * Create a new builder for a message with the given payload.
     * @param payload the payload
     */
    public static <T> RocketMqMessageBuilder<T> withPayload(T payload) {
        return new RocketMqMessageBuilder<>(MessageBuilder.withPayload(payload));
    }

    /**
     * A shortcut factory method for creating a message with the given payload
     * and {@code MessageHeaders}.
     * <p><strong>Note:</strong> the given {@code MessageHeaders} instance is used
     * directly in the new message, i.e. it is not copied.
     * @param payload the payload to use (never {@code null})
     * @param messageHeaders the headers to use (never {@code null})
     * @return the created message
     * @since 4.1
     */
    @SuppressWarnings("unchecked")
    public static <T> Message<T> createMessage(@Nullable T payload, MessageHeaders messageHeaders) {
        return MessageBuilder.createMessage(payload, messageHeaders);
    }
}
