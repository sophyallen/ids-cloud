package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCodeEnum;

/**
 * 签名异常
 *
 * @author admin
 */
public class ThirdServerException extends IdsException {
    private static final long serialVersionUID = 3655050728585279326L;

    private String code;

    public ThirdServerException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public ThirdServerException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.code = errorCodeEnum.getCode();
    }

    public ThirdServerException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause) {
        super(msg, cause);
        this.code = errorCodeEnum.getCode();
    }

    @Override
    public String getCode() {
        return code;
    }
}
