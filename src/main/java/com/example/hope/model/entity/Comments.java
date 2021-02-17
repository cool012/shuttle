package com.example.hope.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 评论实体类
 * @author: DHY
 * @created: 2021/02/17 13:06
 */
@Data
public class Comments implements Serializable {
    private long id;
    private String content;
    private String name;
    private long storeId;
    private String date;
    private long userId;
}
