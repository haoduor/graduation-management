package com.haoduor.graduation.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haoduor.graduation.vo.SubjectVo;

public class ConvertUtil {
    private ConvertUtil() {}

    public static <T> T convert(Class<T> type,  Object value) {
        String json = JSONObject.toJSONString(value);
        return JSON.parseObject(json, type);
    }
}
