package com.dahuaboke.mvc.listener;

import com.dahuaboke.mvc.config.MvcAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Author dahua
 * @Date 2021/4/30 15:34
 * @Description mvc
 */
@WebListener
public class SpringContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("mvc spring listener start, type: war");
        new AnnotationConfigApplicationContext(MvcAutoConfiguration.class);
        System.out.println("mvc spring beans load success, type: war");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("mvc spring listener destroy");
    }
}
