package com.example.hope.common.utils;

import cn.hutool.core.lang.Validator;
import com.example.hope.model.entity.User;

/**
 * @description: 检查工具类
 * @author: DHY
 * @created: 2020/10/29 13:01
 */
public class Checker {

    public static void check_user(User user){
        if (!Validator.isEmail(user.getEmail())){
            throw new IllegalArgumentException("邮箱格式不正确");
        }
        if (user.getType().equals("2")){
            throw new IllegalArgumentException("不能注册为管理员");
        }else if(!user.getType().equals("0") && !user.getType().equals("1")){
            throw new IllegalArgumentException("type参数错误");
        }
    }
}
