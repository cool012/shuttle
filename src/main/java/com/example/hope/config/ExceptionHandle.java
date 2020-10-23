package com.example.hope.config;

import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ReturnMessage<Object> handle(Exception exception){
        // 业务异常 code:-1
        if(exception instanceof BusinessException){
            return ReturnMessageUtil.error(-1, exception.getMessage());
        }
        // 系统异常 code:-2
        return ReturnMessageUtil.error(-2, exception.getMessage());
    }
}
