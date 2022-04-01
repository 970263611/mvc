package com.dahuaboke.mvc.config.parse.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dahuaboke.mvc.config.parse.MvcJsonParser;

import java.util.List;

/**
 * @Author dahua
 * @Date 2021/5/7 9:04
 * @Description mvc
 */
public class MvcFastjsonParser implements MvcJsonParser {

    @Override
    public String toJSONString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public <T> T toObject(String jsonStr, Class<T> clz) {
        return JSON.parseObject(jsonStr, clz);
    }

    @Override
    public <T> List<T> toArray(String jsonStr, Class<T> clz) {
        return JSON.parseArray(jsonStr, clz);
    }
}
