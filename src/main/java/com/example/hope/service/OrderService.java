package com.example.hope.service;

import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void insert(Order order);

    void delete(long id);

    void update(Order order);

    void receive(long id);

    void completed(long id);

    List<OrderDetail> findAll(Map<String,String> option);

    List<OrderDetail> findByPid(long pid, Map<String,String> option);

    List<OrderDetail> findByCid(long cid, Map<String,String> option);

    List<OrderDetail> findByUid(long uid, Map<String,String> option);

    List<OrderDetail> findByType(long id, Map<String,String> option);
}