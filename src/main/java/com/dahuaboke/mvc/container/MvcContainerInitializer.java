package com.dahuaboke.mvc.container;

import com.dahuaboke.mvc.web.filter.MvcFilterFactory;
import com.dahuaboke.mvc.web.listener.MvcListenerFactory;
import com.dahuaboke.mvc.server.MvcWebServer;

/**
 * @Author dahua
 * @Date 2021/5/5 18:17
 * @Description mvc
 */
public interface MvcContainerInitializer {

    void containerInit(Object context);

    MvcWebServer getMvcWebServer();

    void setMvcWebServer(MvcWebServer mvcWebServer);

    void setMvcFilterFactory(MvcFilterFactory mvcFilterFactory);

    void setMvcListenerFactory(MvcListenerFactory mvcListenerFactory);
}
