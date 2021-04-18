package com.example.hope.service.serviceIpm;

import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.service.FileService;
import com.example.hope.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 订单服务实现类
 * @author: DHY
 * @created: 2020/10/25 11:15
 */

@Log4j2
@Service
public class OrderServiceIpm implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserServiceIpm userService;

    @Resource
    private ProductServiceIpm productService;

    @Resource
    private RedisService redisService;

    @Resource
    private FileService fileService;

    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 批量添加订单
     *
     * @param orderList 订单列表
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void insert(List<Orders> orderList, boolean isExpired) {
        orderList.forEach(order -> {
            if (!userService.exist(order.getCid()) || !userService.exist(order.getSid()) || !productService.exist(order.getPid()))
                throw new BusinessException(1, "客户或服务者或产品不存在");
        });
        int res = orderMapper.insertBatch(orderList);
        log.info(LoggerHelper.logger(orderList, res));
        BusinessException.check(res, "添加失败");
        for (Orders order : orderList) {
            // 通过mq发送消息到websocket
            amqpTemplate.convertAndSend("order.exchange", "order.created", order);
            if (isExpired) {
                // 批量设置过期时间
                redisService.expire("order_" + order.getId(), "expire", 10, TimeUnit.MINUTES);
            }
        }
    }

    /**
     * 批量删除订单
     *
     * @param orders 订单列表
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void delete(List<Orders> orders, String token) {
        long userId = JwtUtils.getUserId(token);
        boolean isAdmin = JwtUtils.is_admin(token);
        // 只允许下单用户或管理员在订单为未接单或已完成的状态下删除订单
        for (Orders order : orders) {
            if ((order.getCid() != userId || !isAdmin) && order.getStatus() == 0)
                BusinessException.check(0, "删除无效");
        }
        int res = orderMapper.deleteBatch(orders);
        for (Orders order : orders) {
            log.info(LoggerHelper.logger(order, res));
        }
        BusinessException.check(res, "删除失败");
    }

    /**
     * 根据id删除订单
     *
     * @param id 订单id
     */
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void delete(long id) {
        int res = orderMapper.delete(id, "id");
        log.info(LoggerHelper.logger(id, res));
        BusinessException.check(res, "删除失败");
    }


    /**
     * 根据产品id删除订单
     *
     * @param pid 产品id
     */
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void deleteByPid(long pid) {
        int res = orderMapper.delete(pid, "pid");
        log.info(LoggerHelper.logger(pid, res));
    }

    /**
     * 更新订单
     *
     * @param order 订单id
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void update(Orders order) {
        if (!userService.exist(order.getCid()) || !userService.exist(order.getSid()) || !productService.exist(order.getPid()))
            throw new BusinessException(1, "客户或服务者或产品不存在");
        if (order.getFile().equals("")) order.setFile(null);
        int res = orderMapper.update(order);
        log.info(LoggerHelper.logger(order, res));
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findAll(Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(null, null, null));
    }

    /**
     * 根据产品id查询订单
     *
     * @param option 分页参数
     * @param pid    产品id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #pid.toString() +  #option.toString()")
    public PageInfo<Orders> findByPid(long pid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(pid), "pid", null));
    }

    /**
     * 根据cid查询全部的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #option.toString()")
    public PageInfo<Orders> findByCid(long cid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(cid), "cid", null));
    }

    /**
     * 根据cid查询已下单的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #option.toString()")
    public PageInfo<Orders> findByCidOrOrder(long cid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(cid), "cid", "-1"));
    }

    /**
     * 根据cid查询配送中的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #option.toString()")
    public PageInfo<Orders> findByCidOrPresent(long cid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(cid), "cid", "0"));
    }

    /**
     * 根据cid查询已完成的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #option.toString()")
    public PageInfo<Orders> findByCidOrCompleted(long cid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(cid), "cid", "1"));
    }

    /**
     * 根据cid查询订单
     *
     * @param cid 客户用户id
     * @return 分页包装数据
     */
    @Cacheable(value = "order", key = "methodName + #cid.toString() + 'override'")
    public List<Orders> findByCid(long cid) {
        return orderMapper.select(String.valueOf(cid), "cid", null);
    }

    /**
     * 根据条件搜索cid全部的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #userId.toString() + #option.toString()")
    public PageInfo<Orders> searchByCid(long userId, String start, String end, Long productId, Long serverId, int status, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.searchByCid(userId, start, end, productId, serverId, status));
    }


    /**
     * 根据sid查询订单(已完成)
     *
     * @param option 分页参数
     * @param sid    服务员用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #sid.toString() + #option.toString()")
    public PageInfo<Orders> findBySidOrCompleted(long sid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(sid), "sid", String.valueOf(1)));
    }

    /**
     * 根据sid查询订单(配送中)
     *
     * @param option 分页参数
     * @param sid    服务员用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #sid.toString() + #option.toString()")
    public PageInfo<Orders> findBySidOrPresent(long sid, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select(String.valueOf(sid), "sid", String.valueOf(0)));
    }

    /**
     * 按订单id查询订单
     *
     * @param id 订单id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #id.toString()")
    public Orders findById(long id) {
        List<Orders> orders = orderMapper.select(String.valueOf(id), "id", null);
        if (orders.size() == 0) throw new BusinessException(0, "没有该订单");
        return orders.get(0);
    }

    /**
     * 查询全部未接单订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findByReceive(Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select("-1", "status", null));
    }

    /**
     * 根据条件搜索全部未接单的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> searchByReceive(String start, String end, long serviceId, String address, Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.searchByReceive(start, end, serviceId, address));
    }

    /**
     * 查询全部已接单订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findByCompleted(Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select("1", "status", null));
    }

    /**
     * 查询全部配送中订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> findByPresent(Map<String, String> option) {
        Utils.checkOption(option, Orders.class);
        String orderBy = String.format("orders.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(orderMapper.select("0", "status", null));
    }

    /**
     * 接单
     *
     * @param id 订单id
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public void receive(long id, long userId) {
        findById(id);
        // 减少点数
        userService.reduceScore(userId);
        // 更新订单状态
        int res = orderMapper.receive(id, userId);
        redisService.del("order_" + id);
        // 下单1小时后自动更新为完成状态
        redisService.expire("completed_" + id, "expire", 1, TimeUnit.HOURS);
        BusinessException.check(res, "接单失败");
    }

    /**
     * 主动完成
     *
     * @param orders 订单
     * @param token  Token
     */
    @Override
    @CacheEvict(value = "order", allEntries = true)
    public void completed(Orders orders, String token) {
        long userId = JwtUtils.getUserId(token);
        // 只允许订单用户或管理员修改订单为完成状态
        if (userId != orders.getCid() || JwtUtils.is_admin(token))
            BusinessException.check(0, "非订单用户或管理员操作");
        // 如果存在文件，完成订单时删除文件
        if (orders.getFile() != null && !orders.getFile().equals("")) fileService.remove(orders.getFile());
        // 增加产品销量
        productService.addSales(orders.getPid(), 1);
        int res = orderMapper.completed(orders.getId());
        BusinessException.check(res, "完成订单失败");
    }

    /**
     * 自动完成
     *
     * @param orderId 订单id
     */
    @CacheEvict(value = "order", allEntries = true)
    public void completed(long orderId) {
        Orders orders = findById(orderId);
        // 如果存在文件，完成订单时删除文件
        if (orders.getFile() != null) fileService.remove(orders.getFile());
        // 增加产品销量
        productService.addSales(orders.getPid(), 1);
        int res = orderMapper.completed(orders.getId());
        BusinessException.check(res, "完成订单失败");
    }

    /**
     * 是否存在订单
     *
     * @param id 订单id
     * @return boolean
     */
    @Override
    public boolean exist(long id) {
        return findById(id) != null;
    }
}