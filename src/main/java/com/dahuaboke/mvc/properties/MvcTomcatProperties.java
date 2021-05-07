package com.dahuaboke.mvc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author dahua
 * @Date 2021/5/1 15:01
 * @Description mvc
 */
@ConfigurationProperties(prefix = "mvc.tomcat")
public class MvcTomcatProperties {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
