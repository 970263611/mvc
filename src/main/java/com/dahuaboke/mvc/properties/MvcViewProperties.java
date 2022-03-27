package com.dahuaboke.mvc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author dahua
 * @Date 2021/5/7 22:52
 * @Description mvc
 */
@ConfigurationProperties(prefix = "mvc.view")
public class MvcViewProperties {

    private String prefix = "web/";

    private String suffix = ".html";

    private String[] excludes = {".js",".css",".png", ".jpg", ".jpeg", ".ttf", ".ico"};

    private String debugPath;

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

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public String getDebugPath() {
        return debugPath;
    }

    public void setDebugPath(String debugPath) {
        this.debugPath = debugPath;
    }
}
