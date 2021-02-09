package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.service.FileService;
import com.example.hope.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private FileService fileService;

    @Autowired
    public OrderServiceIpm(OrderMapper orderMapper, UserServiceIpm userService, ProductServiceIpm productService, RedisUtil redisUtil, FileServiceImp fileService) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.productService = productService;
        this.redisUtil = redisUtil;
        this.fileService = fileService;
    }

    /**
     * 添加订单
     *
     * @param orderList
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void insert(List<Orders> orderList, Boolean isExpired) {
//        for (Orders order : orderList) {
//            order.setDate(new Date());
//        }
        int res = orderMapper.insertBatch(orderList);
        // 批量设置过期时间
        if (Boolean.valueOf(isExpired)) {
            for (Orders order : orderList) {
                redisUtil.ins("order_" + order.getId(), "expired", 10, TimeUnit.MINUTES);
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
    public void update(Orders order) {
        int res = orderMapper.update(order);
        log.info("order update -> " + order.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }


    /**
     * 接单
     *
     * @param id
     * @return
     */
    @Override
    @Transient
    @CacheEvict(value = "order", allEntries = true)
    public void receive(long id, long userId) {
        // 减少点数
        userService.reduceScore(userId);
        // 增加销量
        productService.addSales(findById(id).getPid(), 1);
        // 更新订单状态
        int res = orderMapper.receive(id, userId);
        redisUtil.ins("completed_" + id, "expired", 1, TimeUnit.HOURS);
        BusinessException.check(res, "接单失败");
    }

    /**
     * 查询全部订单
     *
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(orderMapper.select(null, null));
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
    public PageInfo<Orders> findByPid(long pid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(orderMapper.select(String.valueOf(pid), "pid"));
    }

    /**
     * 根据cid查询订单
     *
     * @param cid
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findByCid(long cid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(orderMapper.select(String.valueOf(cid), "cid"));
    }

    /**
     * 根据sid查询订单
     *
     * @param sid
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findBySid(long sid, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(orderMapper.select(String.valueOf(sid), "sid"));
    }

    /**
     * 按订单id查询订单
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #id")
    public Orders findById(long id) {
        return orderMapper.select(String.valueOf(id), "id").get(0);
    }

    /**
     * 更新订单状态为完成
     *
     * @param orders
     * @return
     */
    @Override
    @CacheEvict(value = "order", allEntries = true)
    public void completed(Orders orders, String token) {
        long userId = JwtUtils.getUserId(token);
        // 只允许订单服务者或管理员修改订单为完成状态
        if (userId != orders.getSid() || !JwtUtils.is_admin(token))
            BusinessException.check(0,"非订单用户或管理员操作");
        // 如果存在文件，完成订单时删除文件
        if (orders.getFile() != null) fileService.remove(orders.getFile());
        int res = orderMapper.completed(orders.getId());
        BusinessException.check(res, "完成订单失败");
    }
}