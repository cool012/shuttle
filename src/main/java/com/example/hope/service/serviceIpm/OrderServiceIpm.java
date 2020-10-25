package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.OrderDetail;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 订单服务类
 * @author: DHY
 * @created: 2020/10/25 11:15
 */
@Log4j2
@Service
public class OrderServiceIpm implements OrderService {

    private OrderMapper orderMapper;

    @Autowired
    public OrderServiceIpm(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }

    /**
     * 添加订单
     * @param order
     */
    //TODO RabbitMQ消息队列通知
    public void insert(Order order){
        int res = orderMapper.insert(order);
        log.info("order insert -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res,"添加失败");
    }

    /**
     * 删除订单
     * @param id
     */
    public void delete(long id){
        int res = orderMapper.delete(id);
        log.info("order delete id -> " + id + " -> res -> " + res);
        BusinessException.check(res,"删除失败");
    }

    /**
     * 更新订单
     * @param order
     */
    public void update(Order order){
        int res = orderMapper.update(order);
        log.info("order update -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res,"更新失败");
    }

    /**
     * 查询全部订单
     * @return
     */
    public List<OrderDetail> findAll(){
        return orderMapper.findAll(" ");
    }

    /**
     * 根据产品id查询订单
     * @param pid
     * @return
     */
    public List<OrderDetail> findByPid(long pid){
        return orderMapper.findByPid(pid,"pid");
    }

    /**
     * 根据消费者id查询订单
     * @param cid
     * @return
     */
    public List<OrderDetail> findByCid(long cid){
        return orderMapper.findByCid(cid,"cid");
    }

    /**
     * 根据生产者id查询订单
     * @param uid
     * @return
     */
    public List<OrderDetail> findByUid(long uid){
        return orderMapper.findByUid(uid,"uid");
    }

    /**
     * 接单
     * @param id
     * @return
     */
    public void receiveOrder(long id){
        int res = orderMapper.receiveOrder(id);
        log.info("order receiveOrder -> " + id + " -> res -> " + res);
        BusinessException.check(res,"接单失败");
    }

    /**
     * 查询已接单或未结单的订单
     * @param received 已接单/未接单
     * @return
     */
    @Override
    public List<OrderDetail> isReceived(boolean received) {
        return orderMapper.isReceived(received);
    }

    /**
     * 查询已完成或未完成的订单
     * @param completed 已完成/未完成
     * @return
     */
    @Override
    public List<OrderDetail> isCompleted(boolean completed) {
        return orderMapper.isCompleted(completed);
    }

    /**
     * 按服务类型查询订单
     * @param id
     * @param received 已接单/未接单
     * @return
     */
    @Override
    public List<OrderDetail> findByType(long id,boolean received) {
        return orderMapper.findByType(id,received);
    }
}
