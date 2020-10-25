package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 订单详情实体类
 * @author: DHY
 * @created: 2020/10/25 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {

    private long id;

    // 用户名
    private long cid;
    private String username;

    private long pid;
    private String name;

    private Data create_time;
    private String address;
    private String note;
    private String file_url;
    private boolean complete;
    private boolean order_status;
}
