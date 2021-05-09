package com.dahuaboke.mvc.anno;

import java.lang.annotation.*;

/**
 * @Author dahua
 * @Date 2021/5/9 16:20
 * @Description mvc
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcRequestParam {

    String value();
}
