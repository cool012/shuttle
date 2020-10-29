package com.example.hope.common.utils;

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
        return option;
    }
}