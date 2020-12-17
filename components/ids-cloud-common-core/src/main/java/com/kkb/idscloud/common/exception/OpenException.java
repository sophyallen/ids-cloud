package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCode;

/**
 * 基础错误异常
 *
 * @author admin
 */
public class OpenException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private String code = ErrorCode.ERROR.getCode();

    public OpenException() {

    }

    public OpenException(String msg) {
        super(msg);
    }

    public OpenException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public OpenException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
