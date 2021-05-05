package com.dahuaboke.mvc.spring;

import com.dahuaboke.mvc.anno.MvcController;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
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
//        Map<String, Object> restControllers = applicationContext.getBeansWithAnnotation(MvcRestController.class);
        for (Map.Entry<String, Object> controller : controllers.entrySet()) {
            Object instance = controller.getValue();
            Class<?> aClass = instance.getClass();
            MvcRequestMapping mappingClass = aClass.getAnnotation(MvcRequestMapping.class);
            String uri = null;
            if (mappingClass != null) {
                uri = mappingClass.value();
            }
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                MvcRequestMapping mappingMethod = method.getAnnotation(MvcRequestMapping.class);
                if (mappingMethod != null) {
                    String uriAfter = mappingMethod.value();
                    if (!uriAfter.startsWith("/")) {
                        uriAfter = "/" + uriAfter;
                    }
                    uri = uri == null ? uriAfter : uri + uriAfter;
                    if (mappingMap.containsKey(uri)) {
                        throw new RuntimeException("already has this mapping: " + uri);
                    }
                    if (!uri.startsWith("/")) {
                        uri = "/" + uri;
                    }
                    mappingMap.put(uri, aClass.getName() + "#" + method.getName());
                }
            }
        }
    }

    public static Map<String, String> getMappingMap() {
        return mappingMap;
    }
}
