package com.example.hope.config;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static void check(int res,String message){
        if(res < 1){
            throw new BusinessException(-1,message);
        }
    }
}