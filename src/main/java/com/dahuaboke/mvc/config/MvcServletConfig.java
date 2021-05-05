package com.dahuaboke.mvc.config;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @Author dahua
 * @Date 2021/4/30 15:56
 * @Description mvc
 */
@HandlesTypes({Servlet.class, Filter.class, ServletContextListener.class})
public class MvcServletConfig implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        System.out.println("servlet config start, type: war");
    }
}
