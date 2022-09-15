package com.ids.cloud.common.core.exception;

import com.ids.cloud.common.core.constants.ErrorCodeEnum;

/**
 * 签名异常
 *
 * @author zmc
 */
public class ThirdServerException extends IdsException {
    private static final long serialVersionUID = 3655050728585279326L;

    public ThirdServerException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public ThirdServerException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(errorCodeEnum, msg);
    }

    public ThirdServerException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(errorCodeEnum, msg, cause);
    }
}
