package com.dahuaboke.mvc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author dahua
 * @Date 2021/5/7 22:52
 * @Description mvc
 */
@ConfigurationProperties(prefix = "mvc.view")
public class MvcViewProperties {

    private String prefix = "classpath:/templates/";

    private String suffix = ".html";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
