package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 商店实体类
 * @author: DHY
 * @created: 2021/02/03 18:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {

    private long id;
    private String name;
    private long serviceId;
    private long categoryId;
    private String image;
    private int rate;
    private int sales;
    private Service service;
    private Category category;
}
