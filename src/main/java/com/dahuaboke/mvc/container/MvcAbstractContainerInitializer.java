package com.dahuaboke.mvc.container;

import com.dahuaboke.mvc.filter.MvcFilterFactory;
import com.dahuaboke.mvc.listener.MvcListenerFactory;
import com.dahuaboke.mvc.server.MvcWebServer;

/**
 * @Author dahua
 * @Date 2021/5/5 18:20
 * @Description mvc
 */
public abstract class MvcAbstractContainerInitializer implements MvcContainerInitializer {

    protected MvcWebServer mvcWebServer;
    protected MvcFilterFactory mvcFilterFactory;
    protected MvcListenerFactory mvcListenerFactory;

    public MvcWebServer getMvcWebServer() {
        return mvcWebServer;
    }

    public void setMvcWebServer(MvcWebServer mvcWebServer) {
        this.mvcWebServer = mvcWebServer;
    }

    @Override
    public void setMvcFilterFactory(MvcFilterFactory mvcFilterFactory) {
        this.mvcFilterFactory = mvcFilterFactory;
    }

    @Override
    public void setMvcListenerFactory(MvcListenerFactory mvcListenerFactory) {
        this.mvcListenerFactory = mvcListenerFactory;
    }
}
