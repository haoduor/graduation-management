package com.haoduor.graduation.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

public class EncryptedUtil {

    public static String encryptedPassword(String password, String salt) {
        return SecureUtil.md5(SecureUtil.md5(password) + salt);
    }

    public static String getSalt() {
        return RandomUtil.randomString(10);
    }
}
