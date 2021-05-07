package com.dahuaboke.mvc.view;

import com.dahuaboke.mvc.exception.MvcViewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author dahua
 * @Date 2021/5/7 23:47
 * @Description mvc
 */
public class MvcThymeleafViewResolver implements MvcViewResolver {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ServletContext servletContext;

    @Override
    public void resolve(HttpServletRequest request, HttpServletResponse response, String view) throws MvcViewException {
        try {
            WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            templateEngine.process(view, ctx, response.getWriter());
        } catch (IOException e) {
            throw new MvcViewException("can not find view: " + view);
        }
    }
}
