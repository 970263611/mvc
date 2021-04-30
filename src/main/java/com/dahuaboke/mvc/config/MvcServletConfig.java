package com.dahuaboke.mvc.config;

import com.dahuaboke.mvc.filter.MvcFilter;
import com.dahuaboke.mvc.servlet.DispatcherServlet;
import com.dahuaboke.mvc.tomcat.TomcatInsideImpl;
import org.apache.catalina.LifecycleException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @Author dahua
 * @Date 2021/4/30 15:56
 * @Description mvc
 */
@Configuration
@ComponentScan("com.dahuaboke.mvc")
@HandlesTypes({DispatcherServlet.class, MvcFilter.class})
public class MvcServletConfig implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

    }
}
