package com.dahuaboke.mvc.model;

import javax.servlet.ServletContextListener;

/**
 * @Author dahua
 * @Date 2021/5/5 22:44
 * @Description mvc
 */
public class MvcListener {

    private ServletContextListener listener;
    private int order;

    public ServletContextListener getListener() {
        return listener;
    }

    public void setListener(ServletContextListener listener) {
        this.listener = listener;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
