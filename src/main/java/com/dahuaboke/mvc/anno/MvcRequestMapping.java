package com.dahuaboke.mvc.anno;

import java.lang.annotation.*;

/**
 * @Author dahua
 * @Date 2021/4/29 21:28
 * @Description mvc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcRequestMapping {

    String value();
}
