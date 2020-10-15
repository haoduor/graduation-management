package com.haoduor.graduation.config;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean(name = "studentFilter")
    public BitMapBloomFilter studentFilter() {
        return new BitMapBloomFilter(100000);
    }

    @Bean(name = "teacherFilter")
    public BitMapBloomFilter teacherFilter() {
        return new BitMapBloomFilter(100000);
    }
}
