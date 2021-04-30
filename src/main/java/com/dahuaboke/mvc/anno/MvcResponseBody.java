package com.dahuaboke.mvc.anno;

import java.lang.annotation.*;

/**
 * @Author dahua
 * @Date 2021/4/29 21:30
 * @Description mvc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcResponseBody {
}
