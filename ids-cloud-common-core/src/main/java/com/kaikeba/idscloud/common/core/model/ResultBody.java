package com.kaikeba.idscloud.common.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * @author zmc
 */
@ApiModel(description = "响应结果")
@Data
public class ResultBody<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码:00000-请求处理成功")
    private String code;
    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息")
    private String message;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String path;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * traceId
     */
    private String traceId;

    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间")
    private long timestamp = System.currentTimeMillis();

    public ResultBody() {
        super();
    }

    public boolean isOk() {
        return ErrorCodeEnum.OK.getCode().equals(this.code);
    }


    public static <T> ResultBody<T> ok() {
        return new ResultBody<T>().code(ErrorCodeEnum.OK.getCode()).message(ErrorCodeEnum.OK.getMessage());
    }

    public static <T> ResultBody<T> ok(T data) {
        return new ResultBody<T>()
                .code(ErrorCodeEnum.OK.getCode()).message(ErrorCodeEnum.OK.getMessage())
                .data(data);
    }

    public static <T> ResultBody<T> failed() {
        return new ResultBody<T>().code(ErrorCodeEnum.SERVER_ERROR_B0001.getCode()).message(ErrorCodeEnum.SERVER_ERROR_B0001.getMessage());
    }

    public static <T> ResultBody<T> failed(ErrorCodeEnum errorCodeEnum) {
        return new ResultBody<T>().code(errorCodeEnum.getCode()).message(errorCodeEnum.getMessage());
    }

    public static <T> ResultBody<T> failed(ErrorCodeEnum errorCodeEnum, String message) {
        return new ResultBody<T>().code(errorCodeEnum.getCode()).message(message);
    }

    public ResultBody<T> code(String code) {
        this.code = code;
        return this;
    }

    public ResultBody<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResultBody<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResultBody<T> path(String path) {
        this.path = path;
        return this;
    }

    public ResultBody<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    /**
     * 错误信息配置
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("error");

    /**
     * 提示信息国际化
     *
     * @param message
     * @param defaultMessage
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static String i18n(String message, String defaultMessage) {
        return resourceBundle.containsKey(message)?resourceBundle.getString(message):defaultMessage;
    }


}
