package com.dahuaboke.mvc.filter;


import com.dahuaboke.mvc.model.MvcFilter;

import javax.servlet.ServletException;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Author dahua
 * @Date 2021/4/29 22:20
 * @Description mvc
 */
public class MvcFilterFactory {

    /**
     * dahua 避坑
     * tomcat 8.5不可以省略创建和销毁方法，否则会报错
     *
     * @param filterConfig
     * @throws ServletException
     */

    private TreeSet<MvcFilter> filters = new TreeSet<>((o1, o2) -> {
        if (o1.getOrder() > o2.getOrder()) {
            return 1;
        }
        return -1;
    });

    public TreeSet<MvcFilter> getFilters() {
        return filters;
    }

    public void addFilter(MvcFilter filter) {
        filters.add(filter);
    }
}
