package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCodeEnum;

/**
 * 基础错误异常
 *
 * @author admin
 */
public class IdsClientException extends IdsException {

    private static final long serialVersionUID = 3655050728585279326L;

    public IdsClientException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public IdsClientException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(errorCodeEnum, msg);
    }

    public IdsClientException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(errorCodeEnum, msg, cause);
    }
}
