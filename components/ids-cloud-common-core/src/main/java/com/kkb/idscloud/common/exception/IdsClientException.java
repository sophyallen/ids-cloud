package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCodeEnum;

/**
 * 基础错误异常
 *
 * @author admin
 */
public class IdsClientException extends IdsException {

    private static final long serialVersionUID = 3655050728585279326L;

    private String code;

    public IdsClientException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public IdsClientException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.code = errorCodeEnum.getCode();
    }

    public IdsClientException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(msg, cause);
        this.code = errorCodeEnum.getCode();
    }

    @Override
    public String getCode() {
        return code;
    }

}
