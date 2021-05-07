package com.dahuaboke.mvc.server.tomcat;

import com.dahuaboke.mvc.exception.MvcWebServerException;
import com.dahuaboke.mvc.server.MvcAbstractWebServer;
import com.dahuaboke.mvc.servlet.MvcDispatcherServlet;
import com.dahuaboke.mvc.util.SpringBeanUtil;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.server.WebServerException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

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
        context = tomcat.addContext(host, "/", null);
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
