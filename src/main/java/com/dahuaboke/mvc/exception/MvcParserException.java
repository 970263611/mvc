package com.dahuaboke.mvc.exception;

/**
 * @Author dahua
 * @Date 2021/5/9 17:35
 * @Description mvc
 */
public class MvcParserException extends MvcException {

    public MvcParserException(String message) {
        super(message);
    }

    public MvcParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcParserException(Throwable cause) {
        super(cause);
    }
}
