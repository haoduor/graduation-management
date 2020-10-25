package com.haoduor.graduation.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RequestMappingHandlerMapping mapping;

    @RequestMapping("/personal")
    public Object getPersonalInfo() {
        Subject sub = SecurityUtils.getSubject();
        return sub.getPrincipal();
    }

    @ResponseBody
    @GetMapping("/getURL")
    public Object getAllUrl() {
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> mapT = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();

            for (String url : p.getPatterns()) {
                mapT.put("url", url);
            }

//            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            mapT.put("method", method.getMethod().getName()); // 方法名

            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                mapT.put("type", requestMethod.toString());
            }

            list.add(mapT);
        }

        return list;
    }
}
