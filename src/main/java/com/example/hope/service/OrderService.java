package com.example.hope.service;

import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.detail.OrderDetail;
import com.example.hope.model.entity.detail.WaiterOrder;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void insert(List<Order> orderList, Boolean isExpired);

    void delete(long id);

    void update(Order order);

    void receive(long id, String token);

    List<WaiterOrder> findAll(Map<String, String> option);

    List<OrderDetail> findByPid(long pid, Map<String, String> option);

    List<OrderDetail> findByCid(long cid, Map<String, String> option);

    List<OrderDetail> findByUid(long uid, Map<String, String> option);

    List<OrderDetail> findByType(long id, Map<String, String> option);

    OrderDetail findById(long id);

    int completed(long id);
}