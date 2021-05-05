package com.dahuaboke.mvc.model;

import javax.servlet.Filter;

/**
 * @Author dahua
 * @Date 2021/5/4 15:26
 * @Description mvc
 */
public class MvcFilter {

    private String name;
    private String pattern;
    private Filter filter;
    private int order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
