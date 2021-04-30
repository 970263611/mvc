package com.dahuaboke.mvc.tomcat;

import com.dahuaboke.mvc.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

/**
 * @Author dahua
 * @Date 2021/5/1 0:47
 * @Description mvc
 */
public class TomcatInsideImpl {

    public static void init() throws InterruptedException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname("localhost");
        tomcat.setPort(8080);
        Context context = tomcat.addContext("/mvc", "C:\\Users\\dahua\\Desktop\\all\\mvc-test\\target");
        Wrapper wrapper = tomcat.addServlet(context, "mvcDispatcherServlet", new DispatcherServlet());
        // 配置请求拦截转发
        wrapper.addMapping("/");
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
