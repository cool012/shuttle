package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnMessage<T> {

    private Integer code;//错误码
    private String message;//提示信息
    private T date;//返回具体内容

    public ReturnMessage(Integer code, String message, T date) {
        super();
        this.code = code;
        this.message = message;
        this.date = date;
    }
}
