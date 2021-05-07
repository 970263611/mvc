package com.dahuaboke.mvc.view;

import com.dahuaboke.mvc.exception.MvcViewException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author dahua
 * @Date 2021/5/7 22:15
 * @Description mvc
 */
public class MvcDefaultViewResolver implements MvcViewResolver {

    private String prefix;
    private String suffix;

    @Override
    public void resolve(HttpServletRequest request, HttpServletResponse response, String view) throws MvcViewException {
        try {
            ClassPathResource resource = new ClassPathResource(prefix + view + suffix);
            String html = IOUtils.toString(resource.getInputStream(), "UTF-8");
            response.setContentType("text/html;charset=utf-8");
            IOUtils.write(html, response.getOutputStream(), "UTF-8");
        } catch (IOException e) {
            throw new MvcViewException("can not find view: " + view);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
