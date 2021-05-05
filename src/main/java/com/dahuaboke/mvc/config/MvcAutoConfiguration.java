package com.dahuaboke.mvc.config;

import com.dahuaboke.mvc.container.MvcContainerInitializer;
import com.dahuaboke.mvc.container.tomcat.MvcTomcatContainerInitializer;
import com.dahuaboke.mvc.filter.MvcFilterFactory;
import com.dahuaboke.mvc.listener.MvcListenerFactory;
import com.dahuaboke.mvc.properties.MvcProperties;
import com.dahuaboke.mvc.server.MvcWebServer;
import com.dahuaboke.mvc.server.MvcWebServerFactory;
import com.dahuaboke.mvc.server.tomcat.TomcatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;

/**
 * @Author dahua
 * @Date 2021/5/1 15:00
 * @Description mvc
 */
@Configuration
@EnableConfigurationProperties(MvcProperties.class)
@ComponentScan("com.dahuaboke.mvc")
public class MvcAutoConfiguration {

    private final static int TOMCAT_DEFAULT_PORT = 8080;

    @Autowired
    private MvcProperties properties;

    @Bean
    public MvcWebServerFactory mvcWebServerFactory(MvcContainerInitializer mvcContainerInitializer) {
        MvcWebServerFactory mvcWebServerFactory = new MvcWebServerFactory();
        MvcWebServer mvcWebServer = mvcContainerInitializer.getMvcWebServer();
        mvcWebServerFactory.setMvcWebServer(mvcWebServer);
        mvcWebServer.start();
        return mvcWebServerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(MvcWebServer.class)
    public MvcWebServer mvcWebServer() {
        return new TomcatServer(TOMCAT_DEFAULT_PORT);
    }

    @Bean
    @ConditionalOnMissingBean(MvcFilterFactory.class)
    public MvcFilterFactory mvcFilterFactory() {
        return new MvcFilterFactory();
    }

    @Bean
    @ConditionalOnMissingBean(MvcListenerFactory.class)
    public MvcListenerFactory mvcListenerFactory() {
        return new MvcListenerFactory();
    }

    @Bean
    public MvcContainerInitializer mvcContainerInitializer(MvcWebServer mvcWebServer, MvcFilterFactory mvcFilterFactory, MvcListenerFactory mvcListenerFactory) {
        MvcContainerInitializer mvcContainerInitializer = new MvcTomcatContainerInitializer();
        mvcContainerInitializer.setMvcWebServer(mvcWebServer);
        mvcContainerInitializer.setMvcFilterFactory(mvcFilterFactory);
        mvcContainerInitializer.setMvcListenerFactory(mvcListenerFactory);
        mvcContainerInitializer.containerInit(mvcWebServer.getContext());
        return mvcContainerInitializer;
    }
}
