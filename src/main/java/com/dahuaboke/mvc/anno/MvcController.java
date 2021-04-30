package com.dahuaboke.mvc.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author dahua
 * @Date 2021/4/29 21:29
 * @Description mvc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MvcController {
}
