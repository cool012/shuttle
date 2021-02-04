package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 订单实体类
 * @author: DHY
 * @created: 2020/10/23 22:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    private long id;
    private long cid;
    private long sid;
    private long pid;
    private Date date;
    private String address;
    private String note;
    private String file;
    private int status;
    private User client;
    private User service;
    private Product product;
    private String storeName;
}