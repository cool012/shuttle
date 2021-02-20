package com.example.hope.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 广告实体类
 * @author: DHY
 * @created: 2021/02/18 17:15
 */
@Data
public class Ads implements Serializable {
    private long id;
    private String image;
    private long storeId;
}
