package com.example.hope.config.exception;

import com.example.hope.common.utils.ReturnMessageUtil;
import org.apache.ibatis.builder.BuilderException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DuplicateKeyException;
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
        // 唯一性约束
        if(exception instanceof DuplicateKeyException){
            return ReturnMessageUtil.error(-1, "用户已经存在");
        }

        // 系统异常 code:-2
        return ReturnMessageUtil.error(-2, exception.getMessage());
    }
}
