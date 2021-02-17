package com.example.hope.config.exception;

import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        log.error(exception.getMessage());

        // 系统异常 code:-1
        return ReturnMessageUtil.error(-1, "系统异常");
    }

//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(value = UnauthorizedException.class)
//    public ReturnMessage<Object> handle() {
//        return ReturnMessageUtil.error(0, "token过期");
//    }
}
