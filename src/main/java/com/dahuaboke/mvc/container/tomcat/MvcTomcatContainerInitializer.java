package com.dahuaboke.mvc.container.tomcat;

import com.dahuaboke.mvc.container.MvcAbstractContainerInitializer;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.nio.charset.Charset;

/**
 * @Author dahua
 * @Date 2021/5/5 18:11
 * @Description mvc
 */
public class MvcTomcatContainerInitializer extends MvcAbstractContainerInitializer {

    public void containerInit(Object context) {
        StandardContext standardContext = ((StandardContext) context);
        mvcListenerFactory.getListeners().forEach(listener -> {
            standardContext.addApplicationEventListener(listener);
        });
        mvcFilterFactory.getFilters().forEach(filter -> {
            FilterDef filterDef = new FilterDef();
            filterDef.setFilter(filter.getFilter());
            filterDef.setFilterName(filter.getName());
//            filterDef.addInitParameter("", "");
            FilterMap filterMap = new FilterMap();
            filterMap.addURLPatternDecoded(filter.getPattern());
//            filterMap.addServletName("*");
            filterMap.setFilterName(filter.getName());
            filterMap.setCharset(Charset.forName("UTF-8"));
            standardContext.addFilterDef(filterDef);
            standardContext.addFilterMap(filterMap);
        });
    }
}
