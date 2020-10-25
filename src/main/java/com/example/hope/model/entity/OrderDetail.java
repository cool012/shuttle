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
public class OrderDetail extends Order implements Serializable {

    private String name;
    private String waiter;
    private String product;
    private String type;
}
