package com.dahuaboke.mvc.server.tomcat;

import com.dahuaboke.mvc.exception.MvcWebServerException;
import com.dahuaboke.mvc.server.MvcAbstractWebServer;
import com.dahuaboke.mvc.web.servlet.MvcDispatcherServlet;
import com.dahuaboke.mvc.util.SpringBeanUtil;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.boot.web.server.WebServerException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.net.URL;

/**
 * @Author dahua
 * @Date 2021/5/1 0:47
 * @Description mvc
 */
public class MvcTomcatServer extends MvcAbstractWebServer {

    private Tomcat tomcat;
    private Context context;

    public MvcTomcatServer(int port) {
        super(port);
    }

    @Override
    public void init() {
        tomcat = new Tomcat();
        Connector conn = tomcat.getConnector();
        conn.setPort(port);
        conn.setURIEncoding("UTF-8");
        Host host = tomcat.getHost();

        /**
         * 需要指定class路径，否则thymeleaf无法加载静态资源
         */
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        if (resource.getPath().contains(".jar")) {
            /**
             * null设置为默认不加载静态资源，加载器为空加载器
             */
            context = tomcat.addContext(host, "/", null);
            /**
             * 在这里设置预加载器
             */
            WebResourceRoot resources = new StandardRoot(context);
            String path = resource.getPath().replaceFirst("file:", "");
            JarResourceSet jarResourceSet = new JarResourceSet(resources, "/", path.split("!")[0], "/BOOT-INF/classes");
            resources.addPreResources(jarResourceSet);
            context.setResources(resources);
        } else {
            context = tomcat.addContext(host, "/", resource.getPath());
        }
    }


    @Override
    public void start() {
        try {
            Servlet servlet = SpringBeanUtil.getBean(Servlet.class);
            if (!(servlet instanceof MvcDispatcherServlet)) {
                throw new MvcWebServerException("dahuaboke mvc only support MvcDispatcherServlet, can not support custom servlet", null);
            }
            tomcat.addServlet("/", "mvcDispatcherServlet", servlet).addMapping("/");
            tomcat.start();
        } catch (Exception e) {
            throw new MvcWebServerException("tomcat start error", e);
        }
        new Thread(() -> tomcat.getServer().await()).start();
    }

    @Override
    public void stop() throws WebServerException {
        try {
            tomcat.stop();
        } catch (LifecycleException e) {
            throw new MvcWebServerException("tomcat stop error", e);
        }
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ServletContext getServletContext() {
        return context.getServletContext();
    }
}
