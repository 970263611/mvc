package com.dahuaboke.mvc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author dahua
 * @Date 2021/5/1 15:01
 * @Description mvc
 */
@ConfigurationProperties(prefix = "mvc")
public class MvcProperties {

    private String tomcat_port;
}
