package com.example.hope.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnMessage<T> {

    private Integer code;//错误码
    private String message;//提示信息
    private T data;//返回具体内容

    public ReturnMessage(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
