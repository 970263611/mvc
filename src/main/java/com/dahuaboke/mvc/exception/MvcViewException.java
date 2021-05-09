package com.dahuaboke.mvc.exception;

/**
 * @Author dahua
 * @Date 2021/5/7 22:39
 * @Description mvc
 */
public class MvcViewException extends MvcException {
    public MvcViewException(String message) {
        super(message);
    }

    public MvcViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcViewException(Throwable cause) {
        super(cause);
    }
}
