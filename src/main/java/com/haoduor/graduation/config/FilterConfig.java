package com.haoduor.graduation.config;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.haoduor.graduation.model.Period;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class FilterConfig {

    // 学号过滤器
    @Bean(name = "studentFilter")
    public BitMapBloomFilter studentFilter() {
        return new BitMapBloomFilter(100000);
    }

    // 工号过滤器
    @Bean(name = "teacherFilter")
    public BitMapBloomFilter teacherFilter() {
        return new BitMapBloomFilter(100000);
    }

    //
    @Bean(name = "periodMap")
    public Map<String, Period> periodHashMap() {
        return new ConcurrentHashMap<>();
    }
}
