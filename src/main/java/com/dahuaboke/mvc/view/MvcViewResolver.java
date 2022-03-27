package com.dahuaboke.mvc.view;

import com.dahuaboke.mvc.exception.MvcViewException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author dahua
 * @Date 2021/5/7 22:38
 * @Description mvc
 */
public interface MvcViewResolver {

    void resolve(HttpServletRequest request, HttpServletResponse response, String uri) throws MvcViewException;
}
