package com.dahuaboke.mvc.web.servlet;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
                Map handle = mapping.handle(requestURI);
                if (handle != null && handle.containsKey("bean")) {
                    Object bean = handle.get("bean");
                    if (bean != null) {
                        Method method = (Method) handle.get("method");
                        Object[] args = mvcParamParser.parse(method, request);
                        Object invoke = method.invoke(bean, args);
                        if ((Boolean) handle.get("isRest")) {
                            if (invoke != null) {
                                String result = mvcResultParser.parse(invoke);
                                response.setContentType("application/json");
                                response.getWriter().write(result);
                            }
                        } else {
                            if (!(invoke instanceof String)) {
                                throw new MvcResultException("type of ResponseBody result type must be string");
                            }
                            String view = format((String) invoke);
                            mvcViewResolver.resolve(request, response, view);
                        }
                    }
                } else {
                    mvcViewResolver.resolve(request, response, requestURI);
                }
            } catch (Exception e) {
                try {
                    servletResponse.getWriter().write(getStackTrace(e));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    private String format(String uri) {
        if (uri.startsWith("/")) {
            uri = uri.replaceFirst("/", "");
        }
        return uri;
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
