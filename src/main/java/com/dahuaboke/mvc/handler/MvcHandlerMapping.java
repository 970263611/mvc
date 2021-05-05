package com.dahuaboke.mvc.handler;

import com.dahuaboke.mvc.config.parse.MvcResultParser;
import com.dahuaboke.mvc.exception.MvcResultException;
import com.dahuaboke.mvc.spring.AnnoScanUtil;
import com.dahuaboke.mvc.spring.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author dahua
 * @Date 2021/4/29 21:31
 * @Description mvc
 */
@Component
public class MvcHandlerMapping {

    @Autowired
    private MvcResultParser mvcResultParser;

    public void handle(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, MvcResultException {
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/") && requestURI.endsWith("/")) {
            requestURI = requestURI.substring(0, requestURI.length() - 1);
        }
        String classAndMethod = AnnoScanUtil.getMappingMap().get(requestURI);
        if (classAndMethod != null) {
            String[] split = classAndMethod.split("#");
            Class aClass = Class.forName(split[0]);
            Method method = aClass.getDeclaredMethod(split[1]);
            method.setAccessible(true);
            Object bean = SpringBeanUtil.getBean(aClass);
            if (bean != null) {
                Object invoke = method.invoke(bean);
                Boolean isRest = Boolean.valueOf(split[2]);
                if (isRest) {
                    if (invoke != null) {
                        String result = mvcResultParser.parse(invoke);
                        response.getWriter().write(result);
                    }
                } else {
                    if (!(invoke instanceof String)) {
                        throw new MvcResultException("result type must be string");
                    }
                }
            }
        } else {
            response.setStatus(404);
        }
    }

}
