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
            option.put("completed", " ");
        } else {
            String completed = option.get("completed");
            if (!completed.equals("0") && !completed.equals("1") && !completed.equals(" ")) {
                throw new IllegalArgumentException("completed参数错误");
            }else if(!option.containsKey("received")){
                if(option.get("received").equals("0") && option.get("completed").equals("1")){
                    throw new IllegalArgumentException("未接单与已完成冲突");
                }
            }
        }

        if (!option.containsKey("received")) {
            option.put("received", " ");
        } else {
            String received = option.get("received");
            if (!received.equals("0") && !received.equals("1") && !received.equals(" ")) {
                throw new IllegalArgumentException("received参数错误");
            }
        }
        return option;
    }
}