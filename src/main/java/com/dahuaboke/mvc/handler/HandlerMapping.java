package com.dahuaboke.mvc.handler;

import com.dahuaboke.mvc.spring.BeanUtil;
import com.dahuaboke.mvc.spring.URIScanUtil;
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
public class HandlerMapping {

    public void invoke(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        String requestURI = request.getRequestURI();
        String classAndMethod = URIScanUtil.getMappingMap().get(requestURI);
        if (classAndMethod != null) {
            String[] split = classAndMethod.split("#");
            Class aClass = Class.forName(split[0]);
            Method method = aClass.getDeclaredMethod(split[1]);
            method.setAccessible(true);
            Object bean = BeanUtil.getBean(aClass);
            if (bean != null) {
                Object invoke = method.invoke(bean);
                if (invoke != null) {
                    response.getWriter().write(invoke.toString());
                }
            }
        } else {
            response.setStatus(404);
        }
    }

}
