package com.kkb.idscloud.common.exception;

/**
 * @author: zmc
 * @date: 2020-12-18 17:09
 * @description:
 */
public abstract class IdsException extends RuntimeException {

    public IdsException(String msg) {
        super(msg);
    }

    public IdsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public abstract String getCode();
}
