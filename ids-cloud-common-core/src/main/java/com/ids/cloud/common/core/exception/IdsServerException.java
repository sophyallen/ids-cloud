package com.ids.cloud.common.core.exception;

import com.ids.cloud.common.core.constants.ErrorCodeEnum;

/**
 * 提示消息异常
 *
 * @author zmc
 */
public class IdsServerException extends IdsException {
    private static final long serialVersionUID = 4908906410210213271L;

    public IdsServerException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public IdsServerException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(errorCodeEnum, msg);
    }

    public IdsServerException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(errorCodeEnum, msg, cause);
    }
}
