package com.example.hope.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @description: 密码编码器
 * @author: DHY
 * @created: 2020/10/23 22:59
 */
public class Encoder {

    /**
     * 加密
     * @param password
     * @return
     */
    public static String encode(String password) {
        return DigestUtils.md5Hex(password);
    }

}