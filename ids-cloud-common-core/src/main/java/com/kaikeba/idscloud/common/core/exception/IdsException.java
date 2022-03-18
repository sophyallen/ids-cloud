package com.kaikeba.idscloud.common.core.exception;

import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zmc
 * @date: 2020-12-18 17:09
 * @description:
 */
public abstract class IdsException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;

    public IdsException(ErrorCodeEnum errorCodeEnum, String msg) {
        this(errorCodeEnum, msg, null);
    }

    public IdsException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(StringUtils.isBlank(msg) ? errorCodeEnum.getMessage() : msg, cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
