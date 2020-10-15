package com.haoduor.graduation.util;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.RandomUtil;

public class LoginCodeGenerator implements CodeGenerator {
    @Override
    public String generate() {
        return RandomUtil.randomString(4);
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        return false;
    }
}
