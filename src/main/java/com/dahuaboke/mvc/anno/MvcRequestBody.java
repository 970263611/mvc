package com.dahuaboke.mvc.anno;

import java.lang.annotation.*;

/**
 * @Author dahua
 * @Date 2021/4/29 21:29
 * @Description mvc
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcRequestBody {
}
