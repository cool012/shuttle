package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.vo.OrdersVO;

import java.util.List;

public interface OrderService extends BaseService<Orders> {

    boolean insert(List<Orders> orderList, boolean isExpired);

    boolean delete(List<Orders> orders, String token);

    boolean deleteByPid(long pid);

    boolean update(Orders order);

    boolean receive(long id, long userId);

    boolean completed(Orders orders, String token);

    boolean exist(long id);

    IPage<OrdersVO> selectByPage(Query query);

    IPage<OrdersVO> findByPid(long pid, Query query);

    IPage<OrdersVO> findByCid(long cid, Query query);

    IPage<OrdersVO> findByCidOrOrder(long cid, Query query);

    IPage<OrdersVO> findByCidOrPresent(long cid, Query query);

    IPage<OrdersVO> findByCidOrCompleted(long cid, Query query);

    IPage<OrdersVO> findBySidOrCompleted(long sid, Query query);

    IPage<OrdersVO> findBySidOrPresent(long sid, Query query);

    IPage<OrdersVO> findByReceive(Query query);

    IPage<OrdersVO> findByCompleted(Query query);

    IPage<OrdersVO> findByPresent(Query query);

    IPage<OrdersVO> searchByCid(long userId, String start, String end, Long productId, Long serverId, int status,
                                 Query query);

    IPage<OrdersVO> searchByReceive(String start, String end, long serviceId, String address, Query query);

    List<OrdersVO> findByCid(long cid);

    OrdersVO findById(long id);
}