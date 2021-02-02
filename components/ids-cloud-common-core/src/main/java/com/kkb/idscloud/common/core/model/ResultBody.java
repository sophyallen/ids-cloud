package com.kkb.idscloud.common.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkb.idscloud.common.core.constants.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

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

    @JSONField(serialize = false)
    public boolean isOk() {
        return this.code == ErrorCodeEnum.OK.getCode();
    }


    public static ResultBody ok() {
        return new ResultBody().code(ErrorCodeEnum.OK.getCode()).message(ErrorCodeEnum.OK.getMessage());
    }

    public static <T> ResultBody ok(T data) {
        return new ResultBody<T>()
                .code(ErrorCodeEnum.OK.getCode()).message(ErrorCodeEnum.OK.getMessage())
                .data(data);
    }

    public static ResultBody failed() {
        return new ResultBody().code(ErrorCodeEnum.SERVER_ERROR_B0001.getCode()).message(ErrorCodeEnum.SERVER_ERROR_B0001.getMessage());
    }

    public static ResultBody failed(ErrorCodeEnum errorCodeEnum) {
        return new ResultBody().code(errorCodeEnum.getCode()).message(errorCodeEnum.getMessage());
    }

    public static ResultBody failed(ErrorCodeEnum errorCodeEnum, String message) {
        return new ResultBody().code(errorCodeEnum.getCode()).message(message);
    }

    public ResultBody code(String code) {
        this.code = code;
        return this;
    }

    public ResultBody message(String message) {
        this.message = message;
        return this;
    }

    public ResultBody data(T data) {
        this.data = data;
        return this;
    }

    public ResultBody path(String path) {
        this.path = path;
        return this;
    }

    public ResultBody traceId(String traceId) {
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
