package com.dahuaboke.mvc.servlet;

import com.dahuaboke.mvc.handler.HandlerMapping;
import com.dahuaboke.mvc.spring.BeanUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author dahua
 * @Date 2021/4/29 22:19
 * @Description mvc
 */
@WebServlet("/*")
public class DispatcherServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        HandlerMapping mapping = BeanUtil.getBean(HandlerMapping.class);
        if (mapping != null) {
            try {
                mapping.invoke((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

}
