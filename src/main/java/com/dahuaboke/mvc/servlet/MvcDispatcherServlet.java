package com.dahuaboke.mvc.servlet;

import com.dahuaboke.mvc.config.parse.MvcParamParser;
import com.dahuaboke.mvc.config.parse.MvcResultParser;
import com.dahuaboke.mvc.exception.MvcResultException;
import com.dahuaboke.mvc.handler.MvcHandlerMapping;
import com.dahuaboke.mvc.view.MvcViewResolver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author dahua
 * @Date 2021/4/29 22:19
 * @Description mvc
 */
@WebServlet(value = "/")
public class MvcDispatcherServlet extends HttpServlet {

    @Autowired
    private MvcHandlerMapping mapping;
    @Autowired
    private MvcResultParser mvcResultParser;
    @Autowired
    private MvcParamParser mvcParamParser;
    @Autowired
    private MvcViewResolver mvcViewResolver;

    @Override
    public void init(ServletConfig servletConfig) {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (mapping != null) {
            try {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                String requestURI = request.getRequestURI();
                if (requestURI.equals("/favicon.ico")) {
                    return;
                }
                Map handle = mapping.handle(requestURI);
                Object bean = handle.get("bean");
                if (bean != null) {
                    Method method = (Method) handle.get("method");
                    Object[] args = mvcParamParser.parse(method, request);
                    Object invoke = method.invoke(bean, args);
                    if ((Boolean) handle.get("isRest")) {
                        if (invoke != null) {
                            String result = mvcResultParser.parse(invoke);
                            response.getWriter().write(result);
                        }
                    } else {
                        if (!(invoke instanceof String)) {
                            throw new MvcResultException("type of ResponseBody result type must be string");
                        }
                        String view = (String) invoke;
                        if (view.startsWith("/")) {
                            view = view.replaceFirst("/", "");
                        }
                        if (view.endsWith("/")) {
                            view = view.substring(0, view.length() - 1);
                        }
                        mvcViewResolver.resolve(request, response, view);
                    }
                }
            } catch (Exception e) {
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
