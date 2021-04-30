package com.dahuaboke.mvc.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author dahua
 * @Date 2021/4/29 22:20
 * @Description mvc
 */
@WebFilter(urlPatterns = "/*")
public class MvcFilter implements Filter {
    /**
     * dahua 避坑
     * tomcat 8.5不可以省略创建和销毁方法，否则会报错
     *
     * @param filterConfig
     * @throws ServletException
     */

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
