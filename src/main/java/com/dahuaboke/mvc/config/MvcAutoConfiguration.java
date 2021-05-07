package com.dahuaboke.mvc.config;

import com.dahuaboke.mvc.config.parse.MvcDefaultParamParser;
import com.dahuaboke.mvc.config.parse.MvcParamParser;
import com.dahuaboke.mvc.config.parse.MvcResultParser;
import com.dahuaboke.mvc.config.parse.fastjson.MvcResultFastjsonParser;
import com.dahuaboke.mvc.container.MvcContainerInitializer;
import com.dahuaboke.mvc.container.tomcat.MvcTomcatContainerInitializer;
import com.dahuaboke.mvc.filter.MvcFilterFactory;
import com.dahuaboke.mvc.listener.MvcListenerFactory;
import com.dahuaboke.mvc.properties.MvcTomcatProperties;
import com.dahuaboke.mvc.properties.MvcViewProperties;
import com.dahuaboke.mvc.server.MvcWebServer;
import com.dahuaboke.mvc.server.MvcWebServerFactory;
import com.dahuaboke.mvc.server.tomcat.MvcTomcatServer;
import com.dahuaboke.mvc.servlet.MvcDispatcherServlet;
import com.dahuaboke.mvc.view.MvcThymeleafViewResolver;
import com.dahuaboke.mvc.view.MvcViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

/**
 * @Author dahua
 * @Date 2021/5/1 15:00
 * @Description mvc
 */
@Configuration
@EnableConfigurationProperties({MvcTomcatProperties.class, MvcViewProperties.class})
@ComponentScan("com.dahuaboke.mvc")
public class MvcAutoConfiguration {

    private final static int TOMCAT_DEFAULT_PORT = 8080;

    @Autowired
    private MvcTomcatProperties mvcTomcatProperties;
    @Autowired
    private MvcViewProperties mvcViewProperties;

    @Bean
    public MvcWebServerFactory mvcWebServerFactory(MvcContainerInitializer mvcContainerInitializer) {
        MvcWebServerFactory mvcWebServerFactory = new MvcWebServerFactory();
        MvcWebServer mvcWebServer = mvcContainerInitializer.getMvcWebServer();
        mvcWebServerFactory.setMvcWebServer(mvcWebServer);
        mvcWebServer.start();
        return mvcWebServerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(Servlet.class)
    public Servlet servlet() {
        return new MvcDispatcherServlet();
    }

    @Bean
    @ConditionalOnBean(Servlet.class)
    @ConditionalOnMissingBean(MvcWebServer.class)
    public MvcWebServer mvcWebServer() {
        int port = mvcTomcatProperties.getPort() > 0 ? mvcTomcatProperties.getPort() : TOMCAT_DEFAULT_PORT;
        return new MvcTomcatServer(port);
    }

    @Bean
    @ConditionalOnMissingBean(ServletContext.class)
    public ServletContext servletContext(MvcWebServer mvcWebServer) {
        return mvcWebServer.getServletContext();
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

    @Bean
    @ConditionalOnMissingBean(MvcResultParser.class)
    public MvcResultParser mvcResultParser() {
        return new MvcResultFastjsonParser();
    }

    @Bean
    @ConditionalOnMissingBean(MvcParamParser.class)
    public MvcParamParser mvcParamParser() {
        return new MvcDefaultParamParser();
    }

    @Bean
    public ServletContextTemplateResolver servletContextTemplateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(mvcViewProperties.getPrefix());
        templateResolver.setSuffix(mvcViewProperties.getSuffix());
        templateResolver.setCacheTTLMs(3600000L);
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine(ServletContextTemplateResolver templateResolver) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    @ConditionalOnMissingBean(MvcViewResolver.class)
    public MvcViewResolver mvcViewResolver() {
        return new MvcThymeleafViewResolver();
    }
}
