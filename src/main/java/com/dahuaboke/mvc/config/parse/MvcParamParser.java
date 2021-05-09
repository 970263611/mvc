package com.dahuaboke.mvc.config.parse;

import com.dahuaboke.mvc.exception.MvcParserException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author dahua
 * @Date 2021/5/7 10:39
 * @Description mvc
 */
public interface MvcParamParser {

    Object[] parse(Method method, HttpServletRequest request) throws MvcParserException;
}
