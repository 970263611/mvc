package com.dahuaboke.mvc.config.parse.fastjson;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.mvc.config.parse.MvcResultParser;

/**
 * @Author dahua
 * @Date 2021/5/6 0:03
 * @Description mvc
 */
public class MvcResultFastjsonParser implements MvcResultParser {

    @Override
    public String parse(Object obj) {
        return JSON.toJSONString(obj);
    }
}
