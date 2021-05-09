package com.dahuaboke.mvc.config.parse.fastjson;

import com.dahuaboke.mvc.config.parse.MvcJsonParser;
import com.dahuaboke.mvc.config.parse.MvcResultParser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author dahua
 * @Date 2021/5/6 0:03
 * @Description mvc
 */
public class MvcResultFastjsonParser implements MvcResultParser {

    @Autowired
    private MvcJsonParser mvcJsonParser;

    @Override
    public String parse(Object obj) {
        return mvcJsonParser.toJSONString(obj);
    }
}
