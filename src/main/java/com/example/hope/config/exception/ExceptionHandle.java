package com.example.hope.config.exception;

import com.example.hope.common.utils.ReturnMessageUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.BuilderException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Log4j2
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ReturnMessage<Object> handle(Exception exception) {
        // 业务异常 code:0
        if (exception instanceof BusinessException) {
            log.error(exception.getMessage());
            return ReturnMessageUtil.error(0, exception.getMessage());
        }
        // 唯一性约束
        if (exception instanceof DuplicateKeyException) {
            return ReturnMessageUtil.error(0, "用户已经存在");
        }

        exception.printStackTrace();
//        log.error(exception.printStackTrace());

        // 系统异常 code:-1
        return ReturnMessageUtil.error(-1, "系统异常");
    }
}
