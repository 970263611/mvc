package com.dahuaboke.mvc.exception;

/**
 * @Author dahua
 * @Date 2021/5/6 0:12
 * @Description mvc
 */
public class MvcException extends Exception {

    public MvcException() {
    }

    public MvcException(String message) {
        super(message);
    }

    public MvcException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcException(Throwable cause) {
        super(cause);
    }

    public MvcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
