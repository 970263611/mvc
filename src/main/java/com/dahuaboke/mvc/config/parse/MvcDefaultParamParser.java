package com.dahuaboke.mvc.config.parse;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author dahua
 * @Date 2021/5/7 8:57
 * @Description mvc
 */
public class MvcDefaultParamParser implements MvcParamParser {

    @Override
    public Object[] parse(Method method, HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Object[] args = new Object[method.getParameters().length];
        /**
         * 没有requestbody注解，是按照参数顺序来解析的
         */
        return args;
    }
}
