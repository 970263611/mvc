package com.dahuaboke.mvc.handler;

import com.dahuaboke.mvc.util.AnnoScanUtil;
import com.dahuaboke.mvc.util.SpringBeanUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author dahua
 * @Date 2021/4/29 21:31
 * @Description mvc
 */
@Component
public class MvcHandlerMapping {

    public Map handle(String requestURI) {
        if (!requestURI.equals("/") && requestURI.endsWith("/")) {
            requestURI = requestURI.substring(0, requestURI.length() - 1);
        }
        Object[] classAndMethodAndIsRest = AnnoScanUtil.getMappingMap().get(requestURI);
        if (classAndMethodAndIsRest != null) {
            Object bean = SpringBeanUtil.getBean((Class) classAndMethodAndIsRest[0]);
            Method method = (Method) classAndMethodAndIsRest[1];
            method.setAccessible(true);
            return new HashMap() {{
                put("bean", bean);
                put("method", method);
                put("isRest", classAndMethodAndIsRest[2]);
            }};
        }
        return null;
    }
}
