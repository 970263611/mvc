package com.dahuaboke.mvc.exception;

/**
 * @Author dahua
 * @Date 2021/5/6 0:12
 * @Description mvc
 */
public class MvcResultException extends MvcException {

    public MvcResultException(String message) {
        super(message);
    }

    public MvcResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcResultException(Throwable cause) {
        super(cause);
    }
}
