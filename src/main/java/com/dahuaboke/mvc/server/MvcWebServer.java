package com.dahuaboke.mvc.server;

import org.apache.catalina.Context;
import org.springframework.boot.web.server.WebServer;

/**
 * @Author dahua
 * @Date 2021/5/1 16:55
 * @Description mvc
 */
public interface MvcWebServer extends WebServer {

    void init();

    Object getContext();
}
