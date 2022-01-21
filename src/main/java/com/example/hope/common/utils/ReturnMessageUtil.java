package com.example.hope.common.utils;

import com.example.hope.enums.ReturnCode;
import com.example.hope.enums.ReturnContent;
import com.example.hope.model.entity.ReturnMessage;

public class ReturnMessageUtil {

    /**
     * 无异常 请求成功并有具体内容返回
     *
     * @param object
     * @return
     */
    public static ReturnMessage<Object> success(Object object) {
        return new ReturnMessage<>(ReturnCode.OK.getCode(), ReturnContent.SUCCESS.getValue(), object);
    }

    /**
     * 无异常 请求成功并无具体内容返回
     *
     * @return
     */
    public static ReturnMessage<Object> success() {
        return new ReturnMessage<>(ReturnCode.OK.getCode(), ReturnContent.SUCCESS.getValue(), null);
    }

    /**
     * 有自定义错误异常信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static ReturnMessage<Object> error(Integer code, String msg) {
        return new ReturnMessage<>(code, msg, null);
    }

    public static ReturnMessage<Object> status(boolean result) {
        ReturnMessage<Object> message = new ReturnMessage<>();
        if (result) {
            message.setCode(ReturnCode.OK.getCode());
            message.setMessage(ReturnContent.SUCCESS.getValue());
        } else {
            message.setCode(ReturnCode.NO.getCode());
            message.setMessage(ReturnContent.FAILURE.getValue());
        }
        message.setData(null);
        return message;
    }
}
