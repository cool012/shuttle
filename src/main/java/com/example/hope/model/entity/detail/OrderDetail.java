package com.example.hope.model.entity.detail;

import com.example.hope.model.entity.Order;
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

    private String user;
    private String waiter;
    private String product;
    private String type;
    private long sid;
    private String image;
    private int price;
    private String category;
}
