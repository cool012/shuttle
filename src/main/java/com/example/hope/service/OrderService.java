package com.example.hope.service;

import com.example.hope.model.entity.Orders;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void insert(List<Orders> orderList, Boolean isExpired);

    void delete(long id);

    void update(Orders order);

    void receive(long id, long userId);

    PageInfo<Orders> findAll(Map<String, String> option);

    PageInfo<Orders> findByPid(long pid, Map<String, String> option);

    PageInfo<Orders> findByCid(long cid, Map<String, String> option);

    PageInfo<Orders> findBySid(long sid, Map<String, String> option);

    Orders findById(long id);

    void completed(long id);

}