package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Orders;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<Orders> {

    boolean insert(List<Orders> orderList, boolean isExpired);

    boolean delete(List<Orders> orders, String token);

    boolean deleteByPid(long pid);

    boolean update(Orders order);

    boolean receive(long id, long userId);

    boolean completed(Orders orders, String token);

    PageInfo<Orders> page(Map<String, String> option);

    PageInfo<Orders> findByPid(long pid, Map<String, String> option);

    PageInfo<Orders> findByCid(long cid, Map<String, String> option);

    List<Orders> findByCid(long cid);

    PageInfo<Orders> findByCidOrOrder(long cid, Map<String, String> option);

    PageInfo<Orders> findByCidOrPresent(long cid, Map<String, String> option);

    PageInfo<Orders> findByCidOrCompleted(long cid, Map<String, String> option);

    PageInfo<Orders> findBySidOrCompleted(long sid, Map<String, String> option);

    PageInfo<Orders> findBySidOrPresent(long sid, Map<String, String> option);

    Orders findById(long id);

    PageInfo<Orders> findByReceive(Map<String, String> option);

    PageInfo<Orders> findByCompleted(Map<String, String> option);

    PageInfo<Orders> findByPresent(Map<String, String> option);

    boolean exist(long id);

    PageInfo<Orders> searchByCid(long userId, String start, String end, Long productId, Long serverId, int status,
                                 Map<String, String> option);

    PageInfo<Orders> searchByReceive(String start, String end, long serviceId, String address, Map<String, String> option);
}