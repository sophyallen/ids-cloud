package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCodeEnum;

/**
 * 提示消息异常
 *
 * @author admin
 */
public class IdsServerException extends IdsException {
    private static final long serialVersionUID = 4908906410210213271L;

    private String code;

    public IdsServerException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public IdsServerException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.code = errorCodeEnum.getCode();
    }

    public IdsServerException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(msg, cause);
        this.code = errorCodeEnum.getCode();
    }

    @Override
    public String getCode() {
        return code;
    }
}
