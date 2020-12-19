package com.kkb.idscloud.common.core.exception;

import com.kkb.idscloud.common.core.constants.ErrorCodeEnum;

/**
 * @author: zmc
 * @date: 2020-12-18 17:09
 * @description:
 */
public abstract class IdsException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;

    public IdsException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
    }

    public IdsException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(msg, cause);
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
