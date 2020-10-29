package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.OrderDetail;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单服务类
 * @author: DHY
 * @created: 2020/10/25 11:15
 */
@Log4j2
@Service
public class OrderServiceIpm implements OrderService {

    private OrderMapper orderMapper;

    private UserServiceIpm userService;

    private ProductServiceIpm productService;

    @Autowired
    public OrderServiceIpm(OrderMapper orderMapper, UserServiceIpm userService, ProductServiceIpm productService){
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.productService = productService;
    }

    /**
     * 添加订单
     * @param order
     */
    @Override
    @Transient
    public void insert(Order order){
        int res = orderMapper.insert(order);
        log.info("order insert -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res,"添加失败");
    }

    /**
     * 删除订单
     * @param id
     */
    @Override
    @Transient
    public void delete(long id){
        int res = orderMapper.delete(id);
        log.info("order delete id -> " + id + " -> res -> " + res);
        BusinessException.check(res,"删除失败");
    }

    /**
     * 更新订单
     * @param order
     */
    @Override
    @Transient
    public void update(Order order){
        int res = orderMapper.update(order);
        log.info("order update -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res,"更新失败");
    }


    /**
     * 接单（完成订单）
     * @param id
     * @return
     */
    @Override
    @Transient
    public void receive(long id){
        userService.reduceScore(id);
        // TODO 增加销量
        productService.addSales(findById(id,Utils.check_map(new HashMap<>())).getId(),1);
        int res = orderMapper.receive(id);
        log.info("order receiveOrder -> " + id + " -> res -> " + res);
        BusinessException.check(res,"接单失败");
    }


    /**
     * 查询全部订单
     * @param option [sort,order,completed]
     *               - sort 排序 values["create_time"] default:create_time
     *               - order 排序方式 values["0","1"] default:0
     *               - complete 完成 values["0","1","-1"] default:"-1"
     *               0：就绪（已下单，未接单状态） 1：已完成 -1：未完成
     * @return
     */
    @Override
    public List<OrderDetail> findAll(Map<String,String> option){
        Utils.check_map(option);
        List<OrderDetail> list = orderMapper.findAll("all",option.get("sort"),option.get("order"), option.get("completed"));
        return list;
    }

    /**
     * 根据产品id查询订单
     * @param pid
     * @return
     */
    @Override
    public List<OrderDetail> findByPid(long pid,Map<String,String> option){
        Utils.check_map(option);
        return orderMapper.findByPid(pid,"pid",option.get("sort"),option.get("order"), option.get("completed"));
    }

    /**
     * 根据消费者id查询订单
     * @param cid
     * @return
     */
    @Override
    public List<OrderDetail> findByCid(long cid,Map<String,String> option){
        Utils.check_map(option);
        return orderMapper.findByCid(cid,"cid",option.get("sort"),option.get("order"), option.get("completed"));
    }

    /**
     * 根据生产者id查询订单
     * @param uid
     * @return
     */
    @Override
    public List<OrderDetail> findByUid(long uid,Map<String,String> option){
        Utils.check_map(option);
        return orderMapper.findByUid(uid,"uid",option.get("sort"),option.get("order"), option.get("completed"));
    }

    /**
     * 按服务类型查询订单
     * @param id
     * @return
     */
    @Override
    public List<OrderDetail> findByType(long id, Map<String,String> option) {
        Utils.check_map(option);
        return orderMapper.findByType(id,"sid",option.get("sort"),option.get("order"), option.get("completed"));
    }

    @Override
    public OrderDetail findById(long id, Map<String, String> option) {
        Utils.check_map(option);
        return orderMapper.findById(id,"id",option.get("sort"),option.get("order"), option.get("completed"));
    }
}
