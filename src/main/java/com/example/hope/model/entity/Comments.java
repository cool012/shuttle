package com.example.hope.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @description: 评论实体类
 * @author: DHY
 * @created: 2021/02/17 13:06
 */

@Data
@Document
public class Comments implements Serializable {
    private String id;
    @Indexed
    private String content;
    @Indexed
    private String name;
    @Indexed
    private long storeId;
    @Indexed
    private String date;
    @Indexed
    private long userId;
}
