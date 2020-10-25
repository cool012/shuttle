package com.example.hope.service;

import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.OrderDetail;

import java.util.List;

public interface OrderService {

    void insert(Order order);

    void delete(long id);

    void update(Order order);

    List<OrderDetail> findAll();

    List<OrderDetail> findByPid(long pid);

    List<OrderDetail> findByCid(long cid);

    List<OrderDetail> findByUid(long uid);

    void receiveOrder(long id);

    List<OrderDetail> isReceived(boolean received);

    List<OrderDetail> isCompleted(boolean completed);

    List<OrderDetail> findByType(long id,boolean received);

    //TODO 查询按时间排序的订单
}