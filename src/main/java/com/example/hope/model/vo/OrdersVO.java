package com.example.hope.model.vo;

import com.example.hope.model.entity.Orders;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 订单视图实体类
 * @Author duhongyu
 * @Data 2022/1/21 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersVO extends Orders {

    private User client;

    private User service;

    private Product product;

    private String storeName;

    private long businessId;

    private long storeId;
}
