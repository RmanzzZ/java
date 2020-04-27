package com.rman.config.exception;

/**
*没授权的异常处理类
*/
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
