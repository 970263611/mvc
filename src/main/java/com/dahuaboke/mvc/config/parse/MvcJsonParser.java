package com.dahuaboke.mvc.config.parse;

import java.util.List;

/**
 * @Author dahua
 * @Date 2021/5/9 17:03
 * @Description mvc
 */
public interface MvcJsonParser {

    String toJSONString(Object obj);

    <T> T toObject(String jsonStr, Class<T> clz);

    <T> List<T> toArray(String jsonStr, Class<T> clz);
}
