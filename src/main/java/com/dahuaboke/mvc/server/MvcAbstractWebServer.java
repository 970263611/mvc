package com.dahuaboke.mvc.server;

/**
 * @Author dahua
 * @Date 2021/5/5 17:28
 * @Description mvc
 */
public abstract class MvcAbstractWebServer implements MvcWebServer {

    protected int port;

    public MvcAbstractWebServer(int port) {
        this.port = port;
        init();
    }

    @Override
    public abstract void init();
}
