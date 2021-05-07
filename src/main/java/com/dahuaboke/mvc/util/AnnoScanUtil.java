package com.dahuaboke.mvc.util;

import com.dahuaboke.mvc.anno.MvcController;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcResponseBody;
import com.dahuaboke.mvc.anno.MvcRestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
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

    private static Map<String, Object[]> mappingMap = new HashMap();

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

    private void addMapping(Map.Entry<String, Object> controller, boolean isRestController) {
        Object instance = controller.getValue();
        Class<?> aClass = instance.getClass();
        String uriClass = null;
        /**
         * 添加切面后无法使用原生方法获取注解
         * MvcRequestMapping mappingClass = aClass.getAnnotation(MvcRequestMapping.class);
         * 需要使用spring提供的方法获取
         * MvcRequestMapping mappingMethod = AnnotationUtils.findAnnotation(aClass,MvcRequestMapping.class);
         */
        MvcRequestMapping mappingClass = AnnotationUtils.findAnnotation(aClass, MvcRequestMapping.class);
        if (mappingClass != null) {
            uriClass = mappingClass.value();
        }
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            /**
             * 添加切面后无法使用原生方法获取注解
             * MvcRequestMapping mappingClass = method.getAnnotation(MvcRequestMapping.class);
             * 需要使用spring提供的方法获取
             * MvcRequestMapping mappingMethod = AnnotationUtils.findAnnotation(method,MvcRequestMapping.class);
             */
            MvcRequestMapping mappingMethod = AnnotationUtils.findAnnotation(method, MvcRequestMapping.class);
            if (mappingMethod != null) {
                String uriMethod = mappingMethod.value();
                if (!uriMethod.startsWith("/")) {
                    uriMethod = "/" + uriMethod;
                }
                if (uriMethod.endsWith("/")) {
                    uriMethod = uriMethod.substring(0, uriMethod.length() - 1);
                }
                String uri = uriMethod;
                if (uriClass != null) {
                    if (uriClass.endsWith("/")) {
                        uriClass = uriClass.substring(0, uriClass.length() - 1);
                    }
                    uri = uriClass + uri;
                }
                if (mappingMap.containsKey(uri)) {
                    throw new RuntimeException("already has this mapping: " + uri);
                }
                if (!uri.startsWith("/")) {
                    uri = "/" + uri;
                }
                boolean isRest = true;
                if (!isRestController) {
                    /**
                     * 添加切面后无法使用原生方法获取注解
                     * MvcResponseBody responseBody = method.getAnnotation(MvcResponseBody.class);
                     * 需要使用spring提供的方法获取
                     * MvcResponseBody responseBody = AnnotationUtils.findAnnotation(method, MvcResponseBody.class);
                     */
                    MvcResponseBody responseBody = AnnotationUtils.findAnnotation(method, MvcResponseBody.class);
                    if (responseBody == null) {
                        isRest = false;
                    }
                }
                mappingMap.put(uri, new Object[]{aClass, method, isRest});
            }
        }
    }

    public static Map<String, Object[]> getMappingMap() {
        return mappingMap;
    }
}
