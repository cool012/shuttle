package com.example.hope.common.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.example.hope.model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @description: 工具类
 * @author: DHY
 * @created: 2020/10/27 13:01
 */
public class Utils {


    public static Map<String, String> check_map(Map<String, String> option) {

        if (!option.containsKey("sort")) {
            option.put("sort", "create_time");
        } else {
            if (!option.get("sort").equals("create_time")) {
                throw new IllegalArgumentException("sort参数错误");
            }
        }

        if (!option.containsKey("order")) {
            option.put("order", "ASC");
        } else {
            String order = option.get("order");
            if (!order.equals("ASC") && !order.equals("DESC")) {
                throw new IllegalArgumentException("order参数错误");
            }
        }

        if (!option.containsKey("completed")) {
            option.put("completed", "-1");
        } else {
            String completed = option.get("completed");
            if (!completed.equals("0") && !completed.equals("1") && !completed.equals("-1")) {
                throw new IllegalArgumentException("completed参数错误");
            }
        }

        if (!option.containsKey("pageNo")) {
            option.put("pageNo", "1");
        }

        if (!option.containsKey("pageSize")) {
            option.put("pageSize", "10");
        }

        return option;
    }

    public static void check_user(User user) {
        if (!Validator.isMobile(user.getPhone())) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
    }

    public static String encode(String password) {
        return DigestUtils.md5Hex(password);
    }

    /**
     * 根据时间戳生成订单号
     */
    public static String getOrderNo(long userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime localDateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return df.format(localDateTime) + "id=" + userId;
    }

}