package com.example.hope.service;

import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.detail.OrderDetail;
import com.example.hope.model.entity.detail.WaiterOrder;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void insert(List<Order> orderList, Boolean isExpired);

    void delete(long id);

    void update(Order order);

    void receive(long id, String token);

    PageInfo<WaiterOrder> findAll(Map<String, String> option);

    PageInfo<OrderDetail> findByPid(long pid, Map<String, String> option);

    PageInfo<OrderDetail> findByCid(long cid, Map<String, String> option);

    PageInfo<OrderDetail> findByUid(long uid, Map<String, String> option);

    PageInfo<OrderDetail> findByType(long id, Map<String, String> option);

    OrderDetail findById(long id);

    int completed(long id);
}