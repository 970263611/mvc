package com.dahuaboke.mvc.spring;

import com.dahuaboke.mvc.anno.MvcController;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcResponseBody;
import com.dahuaboke.mvc.anno.MvcRestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author dahua
 * @Date 2021/4/29 21:43
 * @Description mvc
 */
@Component
public class AnnoScanUtil implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, String> mappingMap = new HashMap();

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(MvcController.class);
        Map<String, Object> restControllers = applicationContext.getBeansWithAnnotation(MvcRestController.class);
        for (Map.Entry<String, Object> controller : controllers.entrySet()) {
            addMapping(controller, false);
        }
        for (Map.Entry<String, Object> controller : restControllers.entrySet()) {
            addMapping(controller, true);
        }
    }

    private void addMapping(Map.Entry<String, Object> controller, boolean isRest) {
        Object instance = controller.getValue();
        Class<?> aClass = instance.getClass();
        String uriClass = null;
        MvcRequestMapping mappingClass = aClass.getAnnotation(MvcRequestMapping.class);
        if (mappingClass != null) {
            uriClass = mappingClass.value();
        }
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            MvcRequestMapping mappingMethod = method.getAnnotation(MvcRequestMapping.class);
            if (mappingMethod != null) {
                String uriMethod = mappingMethod.value();
                if (!uriMethod.startsWith("/")) {
                    uriMethod = "/" + uriMethod;
                }
                String uri = uriClass == null ? uriMethod : uriClass + uriMethod;
                if (mappingMap.containsKey(uri)) {
                    throw new RuntimeException("already has this mapping: " + uri);
                }
                if (!uri.startsWith("/")) {
                    uri = "/" + uri;
                }
                if (!isRest) {
                    MvcResponseBody responseBody = method.getAnnotation(MvcResponseBody.class);
                    if (responseBody != null) {
                        isRest = true;
                    }
                }
                mappingMap.put(uri, aClass.getName() + "#" + method.getName() + "#" + isRest);
            }
        }
    }

    public static Map<String, String> getMappingMap() {
        return mappingMap;
    }
}
