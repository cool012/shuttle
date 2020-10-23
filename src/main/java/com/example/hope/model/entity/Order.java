package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 订单实体类
 * @author: DHY
 * @created: 2020/10/23 22:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private long id;
    private long consumer_user_id;
    private long producer_user_id;
    private Data create_time;
    private String address;
    private String note;
    private String file_url;
    private boolean complete;
    private boolean order_status;
}