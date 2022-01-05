package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.enums.OrdersState;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.User;
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
import java.util.stream.Collectors;

/**
 * @description: 订单服务实现类
 * @author: DHY
 * @created: 2020/10/25 11:15
 */

@Log4j2
@Service
public class OrderServiceIpm extends BaseServiceImp<Orders, OrderMapper> implements OrderService {

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
    public boolean insert(List<Orders> orderList, boolean isExpired) {
        List<Long> cidList = orderList.stream().map(Orders::getCid).distinct().collect(Collectors.toList());
        List<Long> sidList = orderList.stream().map(Orders::getSid).distinct().collect(Collectors.toList());
        List<Long> pidList = orderList.stream().map(Orders::getPid).distinct().collect(Collectors.toList());
        int cidSize = userService.list(new LambdaQueryWrapper<User>().in(User::getId, cidList)).size();
        int sidSize = userService.list(new LambdaQueryWrapper<User>().in(User::getId, cidList)).size();
        int pidSize = productService.list(new LambdaQueryWrapper<Product>().in(Product::getId, pidList)).size();
        BusinessException.check(cidSize != cidList.size(), "客户不存在");
        BusinessException.check(sidSize != sidList.size(), "服务者不存在");
        BusinessException.check(pidSize != pidList.size(), "产品不存在");
        for (Orders order : orderList) {
            // 通过mq发送消息到websocket
            amqpTemplate.convertAndSend("order.exchange", "order.created", order);
            if (isExpired) {
                // 批量设置过期时间
                redisService.expire("order_" + order.getId(), "expire", 10, TimeUnit.MINUTES);
            }
        }
        return this.saveBatch(orderList);
    }

    /**
     * 批量删除订单
     *
     * @param orders 订单列表
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public boolean delete(List<Orders> orders, String token) {
        long userId = JwtUtils.getUserId(token);
        boolean isAdmin = JwtUtils.is_admin(token);
        // 只允许下单用户或管理员在订单为未接单或已完成的状态下删除订单
        boolean state = orders.stream().anyMatch(x -> (x.getCid() != userId || !isAdmin) && x.getStatus() == 0);
        BusinessException.check(state, "删除无效");
        return this.removeBatchByIds(orders);
    }

    /**
     * 根据id删除订单
     *
     * @param id 订单id
     */
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public boolean delete(long id) {
        return this.removeById(id);
    }


    /**
     * 根据产品id删除订单
     *
     * @param pid 产品id
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public boolean deleteByPid(long pid) {
        return this.remove(this.getQueryWrapper(Orders::getPid, pid));
    }

    /**
     * 更新订单
     *
     * @param order 订单id
     */
    @Override
    @Transactional
    @CacheEvict(value = "order", allEntries = true)
    public boolean update(Orders order) {
        BusinessException.check(!userService.exist(order.getCid()), "客户不存在");
        BusinessException.check(!userService.exist(order.getSid()), "服务员不存在");
        BusinessException.check(!productService.exist(order.getPid()), "产品不存在");
        if (order.getFile().equals("")) order.setFile(null);
        return this.updateById(order);
    }

    /**
     * 查询全部订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #option.toString()")
    public PageInfo<Orders> page(Map<String, String> option) {
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
    @Override
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
    public boolean receive(long id, long userId) {
        Orders orders = findById(id);
        orders.setSid(userId);
        // 减少点数
        userService.reduceScore(userId);
        redisService.del("order_" + id);
        // 下单1小时后自动更新为完成状态
        redisService.expire("completed_" + id, "expire", 1, TimeUnit.HOURS);
        return this.updateById(orders);
    }

    /**
     * 主动完成
     *
     * @param orders 订单
     * @param token  Token
     */
    @Override
    @CacheEvict(value = "order", allEntries = true)
    public boolean completed(Orders orders, String token) {
        long userId = JwtUtils.getUserId(token);
        // 只允许订单用户或管理员修改订单为完成状态
        boolean state = userId != orders.getCid() || !JwtUtils.is_admin(token);
        BusinessException.check(state, "非订单用户或管理员操作");
        // 如果存在文件，完成订单时删除文件
        if (orders.getFile() != null && !orders.getFile().equals("")) fileService.remove(orders.getFile());
        // 增加产品销量
        productService.addSales(orders.getPid(), 1);
        orders.setStatus(OrdersState.COMPLETE.getCode());
        return this.updateById(orders);
    }

    /**
     * 自动完成
     *
     * @param orderId 订单id
     */
    @CacheEvict(value = "order", allEntries = true)
    public boolean completed(long orderId) {
        Orders orders = findById(orderId);
        // 如果存在文件，完成订单时删除文件
        if (orders.getFile() != null) fileService.remove(orders.getFile());
        // 增加产品销量
        productService.addSales(orders.getPid(), 1);
        orders.setStatus(OrdersState.COMPLETE.getCode());
        return this.updateById(orders);
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