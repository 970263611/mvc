package com.dahuaboke.mvc.config.parse;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.mvc.anno.MvcRequestBody;
import com.dahuaboke.mvc.anno.MvcRequestHeader;
import com.dahuaboke.mvc.anno.MvcRequestParam;
import com.dahuaboke.mvc.exception.MvcParserException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Author dahua
 * @Date 2021/5/7 8:57
 * @Description mvc
 */
public class MvcDefaultParamParser implements MvcParamParser {

    @Autowired
    private MvcJsonParser mvcJsonParser;

    /**
     * 原生支持：
     * 单requestbody
     * get和post都支持，需要添加json请求头
     * get和post支持单对象转换和单参数
     * get和post支持单对象转换和多参数
     * get和post支持单对象转换其他参数对象转换
     * 多个requestbody
     * 不支持
     * 没有requestbody
     * 错误参数可以解析
     * 单对象参数可以直接将参数转换成对象
     * 多参数可以直接将参数转换成对象和单个属性
     * 多参数可以直接将参数转换成多个对象
     * 多参数可以直接将参数转换成多个对象和单个属性
     * 注：
     * 有requestbody注解必须设置请求头json且只能解析json格式的数据
     * 没有requestbody注解无法解析json类型参数
     * 没有requestbody注解是按照顺序解析，不存在消费过后续不能消费的问题
     * 没有requestbody注解不关心请求头是不是json
     * 有requestbody注解，后续url上的参数可以解析
     * 有requestbody注解，同在body体里面的参数无法被外层参数获取
     * 有requestbody注解，类型不匹配但是如果可以强制转换也可以解析
     * <p>
     * 这里做了简化处理 不考虑请求头，不考虑requestBody注解的个数，不消费完就无法消费同样字段
     * 当同一个参数上有多种注解，加载顺序如下
     * 1.MvcRequestBody
     * 2.MvcRequestParam
     * 3.MvcRequestHeader
     *
     * @param method
     * @param request
     * @return
     */

    @Override
    public Object[] parse(Method method, HttpServletRequest request) throws MvcParserException {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        Map<String, String> params = parseParams(request);
        Map<String, String> headers = parseHeaders(request);
        for (int a = 0; a < parameters.length; a++) {
            Class paramType = parameters[a].getType();
            if (paramType.getName().startsWith("java.")) {
                if (paramType.equals(List.class) || Arrays.asList(paramType.getInterfaces()).contains(List.class)) {
                    args[a] = getRequestJson(request, true, Object.class);
                    continue;
                }
                if (paramType.equals(Map.class) || Arrays.asList(paramType.getInterfaces()).contains(Map.class)) {
                    args[a] = getRequestJson(request, false, null);
                    continue;
                }
            }
            /**
             * 这里可以这么获取是因为handlerMapping放置method的时候采用的反射是直接获取到了代理之前的类
             */
            MvcRequestBody mvcRequestBody = parameters[a].getAnnotation(MvcRequestBody.class);
            MvcRequestParam mvcRequestParam = parameters[a].getAnnotation(MvcRequestParam.class);
            MvcRequestHeader mvcRequestHeader = parameters[a].getAnnotation(MvcRequestHeader.class);
            if (mvcRequestBody != null) {
                args[a] = getRequestJson(request, false, paramType);
            } else if (mvcRequestParam != null) {
                String mvcRequestParamValue = mvcRequestParam.value();
                String value = params.get(mvcRequestParamValue);
                args[a] = changeArg(value, paramType);
            } else if (mvcRequestHeader != null) {
                String mvcRequestHeaderValue = mvcRequestHeader.value();
                args[a] = headers.get(mvcRequestHeaderValue.toLowerCase());
            } else {
                String paramName = parameters[a].getName();
                String param = params.get(paramName);
                if (param != null) {
                    args[a] = changeArg(param, paramType);
                } else {
                    args[a] = changeArg(mvcJsonParser.toJSONString(params), paramType);
                }
            }
        }
        return args;
    }

    private <T> T changeArg(String v, Class<T> clz) throws MvcParserException {
        if (v != null) {
            try {
                if (clz.equals(String.class)) {
                    return (T) v;
                }
                if (clz.equals(int.class) || clz.equals(Integer.class)) {
                    return (T) ((Integer) Integer.parseInt(v));
                }
                if (clz.equals(short.class) || clz.equals(Short.class)) {
                    return (T) ((Short) Short.parseShort(v));
                }
                if (clz.equals(long.class) || clz.equals(Long.class)) {
                    return (T) ((Long) Long.parseLong(v));
                }
                if (clz.equals(float.class) || clz.equals(Float.class)) {
                    return (T) ((Float) Float.parseFloat(v));
                }
                if (clz.equals(double.class) || clz.equals(Double.class)) {
                    return (T) ((Double) Double.parseDouble(v));
                }
                if (clz.equals(char.class) || clz.equals(Character.class)) {
                    return (T) (v.toCharArray());
                }
                if (clz.equals(boolean.class) || clz.equals(Boolean.class)) {
                    return (T) ((Boolean) Boolean.parseBoolean(v));
                }
                if (clz.equals(byte.class) || clz.equals(Byte.class)) {
                    return (T) ((Byte) Byte.parseByte(v));
                }
                return mvcJsonParser.toObject(v, clz);
            } catch (Exception e) {
                throw new MvcParserException("param type mismatching");
            }
        }
        return null;
    }

    private Map<String, String> parseParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            /**
             * 这里忽略多个参数名字相同情况
             */
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    private Map<String, String> parseHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> result = new LinkedHashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    private Object getRequestJson(HttpServletRequest request, boolean isList, Class clz) throws MvcParserException {
        try {
            String json = getRequestJsonString(request);
            if (isList && clz != null) {
                return mvcJsonParser.toArray(json, clz);
            } else if (!isList && clz != null) {
                return mvcJsonParser.toObject(json, clz);
            }
            return mvcJsonParser.toObject(json, Map.class);
        } catch (Exception e) {
            throw new MvcParserException("param is null or type mismatching");
        }
    }

    private String getRequestJsonString(HttpServletRequest request) throws IOException, MvcParserException {
        String method = request.getMethod();
        if (method.equals("POST")) {
            return getRequestPostStr(request);
        }
        throw new MvcParserException("only post requests support parameter conversion of Map and List types");
    }

    private String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }


    private byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(3);
            add(3);
        }};
//        Map map = new HashMap() {{
//            put("1", 1);
//            put("2", 2);
//            put("3", 3);
//        }};
        System.out.println(JSON.toJSONString(list));
    }
}
