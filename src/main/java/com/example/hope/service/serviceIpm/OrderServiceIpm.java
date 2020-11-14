package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.detail.OrderDetail;
import com.example.hope.model.entity.detail.WaiterOrder;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.service.OrderService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private RedisUtil redisUtil;

    @Autowired
    public OrderServiceIpm(OrderMapper orderMapper, UserServiceIpm userService, ProductServiceIpm productService,RedisUtil redisUtil) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.productService = productService;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加订单
     *
     * @param orderList
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void insert(List<Order> orderList,Boolean isExpired) {
        for (Order order : orderList) {
            order.setCreate_time(new Date());
            // 代替为空的用户
            order.setUid(20);
        }
        int res = 0;
        if (orderList.size() == 1) {
            res = orderMapper.insert(orderList.get(0));
            // 设置过期时间
            if(Boolean.valueOf(isExpired)) {
                redisUtil.ins(String.valueOf(orderList.get(0).getId()), "expired", 1, TimeUnit.MINUTES);
            }
        } else if (orderList.size() > 1) {
            res = orderMapper.insertBatch(orderList);
            // 批量设置过期时间
            if(Boolean.valueOf(isExpired)) {
                for (Order order : orderList) {
                    redisUtil.ins(String.valueOf(order.getId()), "expired", 1, TimeUnit.MINUTES);
                }
            }
        }
        log.info("order insert -> " + orderList.toString() + " -> res -> " + res);
        BusinessException.check(res, "添加失败");
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void delete(long id) {
        int res = orderMapper.delete(id);
        log.info("order delete id -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新订单
     *
     * @param order
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void update(Order order) {
        int res = orderMapper.update(order);
        log.info("order update -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }


    /**
     * 接单（完成订单）
     *
     * @param id
     * @return
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void receive(long id, String token) {
        long userId = JwtUtils.getUserId(token);
        // 减少点数
        userService.reduceScore(userId);
        // 增加销量
        productService.addSales(findById(id).getPid(), 1);
        // 更新订单状态
        int res = orderMapper.receive(id, userId);
        BusinessException.check(res, "接单失败");
    }

    /**
     * 查询全部订单
     *
     * @param option [sort,order,completed]
     *               - sort 排序 values["create_time"] default:create_time
     *               - order 排序方式 values["0","1"] default:0
     *               - complete 完成 values["0","1","-1"] default:"-1"
     *               0：就绪（已下单，未接单状态） 1：已完成 -1：未完成
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public List<WaiterOrder> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return orderMapper.findAll("all", option.get("sort"), option.get("order"), option.get("completed"));
    }

    /**
     * 根据产品id查询订单
     *
     * @param pid
     * @return
     */
    // value代表缓存名称 key代表键 在redis中以 value::key 的形式表示redis的key
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public List<OrderDetail> findByPid(long pid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return orderMapper.findByPid(pid, "pid", option.get("sort"), option.get("order"), option.get("completed"));
    }

    /**
     * 根据消费者id查询订单
     *
     * @param cid
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public List<OrderDetail> findByCid(long cid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return orderMapper.findByCid(cid, "cid", option.get("sort"), option.get("order"), option.get("completed"));
    }

    /**
     * 根据生产者id查询订单
     *
     * @param uid
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public List<OrderDetail> findByUid(long uid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return orderMapper.findByUid(uid, "uid", option.get("sort"), option.get("order"), option.get("completed"));
    }

    /**
     * 按服务类型查询订单
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public List<OrderDetail> findByType(long id, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return orderMapper.findByType(id, "sid", option.get("sort"), option.get("order"), option.get("completed"));
    }

    @Override
    @Cacheable(value = "order", key = "methodName + #id")
    public OrderDetail findById(long id) {
        return orderMapper.findById(id);
    }
}