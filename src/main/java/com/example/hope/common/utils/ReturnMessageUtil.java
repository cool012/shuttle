package com.example.hope.common.utils;

import com.example.hope.model.entity.ReturnMessage;

public class ReturnMessageUtil {

    /**
     * 无异常 请求成功并有具体内容返回
     * @param object
     * @return
     */
    public static ReturnMessage<Object> success(Object object) {
        ReturnMessage<Object> message = new ReturnMessage<Object>(1,"success",object);
        return message;
    }
    /**
     * 无异常 请求成功并无具体内容返回
     * @return
     */
    public static ReturnMessage<Object> success() {
        ReturnMessage<Object> message = new ReturnMessage<Object>(1,"success",null);
        return message;
    }
    /**
     * 有自定义错误异常信息
     * @param code
     * @param msg
     * @return
     */
    public static ReturnMessage<Object> error(Integer code,String msg) {
        ReturnMessage<Object> message = new ReturnMessage<Object>(code,msg,null);
        return message;
    }
}
