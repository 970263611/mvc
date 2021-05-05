package com.dahuaboke.mvc.exception;

import org.springframework.boot.web.server.WebServerException;

/**
 * @Author dahua
 * @Date 2021/5/6 0:18
 * @Description mvc
 */
public class MvcWebServerException extends WebServerException {

    public MvcWebServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
