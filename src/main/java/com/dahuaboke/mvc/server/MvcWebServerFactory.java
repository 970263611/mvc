package com.dahuaboke.mvc.server;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

/**
 * @Author dahua
 * @Date 2021/5/5 16:58
 * @Description mvc
 */
public class MvcWebServerFactory implements ServletWebServerFactory {

    private MvcWebServer mvcWebServer;

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        return mvcWebServer;
    }

    public void setMvcWebServer(MvcWebServer mvcWebServer) {
        this.mvcWebServer = mvcWebServer;
    }
}
