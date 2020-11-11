package com.haoduor.graduation.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

public class EncryptedUtil {

    /**
     * 生成盐密码
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String encryptedPassword(String password, String salt) {
        return SecureUtil.md5(SecureUtil.md5(password) + salt);
    }

    /**
     * 生成随机盐
     * @return
     */
    public static String getSalt() {
        return RandomUtil.randomString(10);
    }
}
