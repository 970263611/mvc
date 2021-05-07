package com.dahuaboke.mvc.config.parse;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author dahua
 * @Date 2021/5/7 9:04
 * @Description mvc
 */
@Component
public class MvcFastjsonParser {

    public String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public <T> T toObject(String jsonStr, Class<T> clz) {
        return JSON.parseObject(jsonStr, clz);
    }

    public <T> List<T> toArray(String jsonStr, Class<T> clz) {
        return JSON.parseArray(jsonStr, clz);
    }
}
