package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.PageUtils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.enums.OrdersState;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.User;
import com.example.hope.model.mapper.OrderMapper;
import com.example.hope.model.vo.OrdersVO;
import com.example.hope.service.other.FileService;
import com.example.hope.service.business.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
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
        return this.removeByIds(orders);
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
    @Cacheable(value = "order", key = "methodName + #query.toString()")
    public IPage<OrdersVO> selectByPage(Query query) {
        return this.pageByWrapper(query, new QueryWrapper<>());
    }

    private IPage<OrdersVO> pageByWrapper(Query query, Wrapper<Orders> wrapper) {
        IPage<OrdersVO> page = PageUtils.getQuery(query);
        return this.baseMapper.selectByPage(page, wrapper);
    }

    /**
     * 根据产品id查询订单
     *
     * @param option 分页参数
     * @param pid    产品id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #pid.toString() + #query.toString()")
    public IPage<OrdersVO> findByPid(long pid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.pid", pid);
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据cid查询全部的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #query.toString()")
    public IPage<OrdersVO> findByCid(long cid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.cid", cid);
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据cid查询已下单的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #query.toString()")
    public IPage<OrdersVO> findByCidOrOrder(long cid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.cid", cid)
                .eq("orders.status", OrdersState.HAVE.getCode());
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据cid查询配送中的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #query.toString()")
    public IPage<OrdersVO> findByCidOrPresent(long cid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.cid", cid)
                .eq("orders.status", OrdersState.SERVICING.getCode());
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据cid查询已完成的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + #query.toString()")
    public IPage<OrdersVO> findByCidOrCompleted(long cid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.cid", cid)
                .eq("orders.status", OrdersState.COMPLETE.getCode());
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据cid查询订单
     *
     * @param cid 客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #cid.toString() + 'override'")
    public List<OrdersVO> findByCid(long cid) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>().eq("orders.cid", cid);
        return orderMapper.selectByList(wrapper);
    }

    /**
     * 根据条件搜索cid全部的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #userId.toString() + #query.toString()")
    public IPage<OrdersVO> searchByCid(long userId, String start, String end, Long productId, Long businessId, int status, Query query) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.status", status)
                .eq("orders.cid", userId);
        if (start != null && end != null) {
            wrapper.between("orders.date", start, end);
        }
        if (businessId != 0) {
            wrapper.eq("orders.sid", businessId);
        }
        if (productId != 0) {
            wrapper.eq("orders.pid", productId);
        }
        return this.pageByWrapper(query, wrapper);
    }


    /**
     * 根据sid查询订单(已完成)
     *
     * @param option 分页参数
     * @param sid    服务员用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #sid.toString() + #query.toString()")
    public IPage<OrdersVO> findBySidOrCompleted(long sid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.sid", sid);
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据sid查询订单(配送中)
     *
     * @param option 分页参数
     * @param sid    服务员用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #sid.toString() + #query.toString()")
    public IPage<OrdersVO> findBySidOrPresent(long sid, Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.sid", sid)
                .eq("orders.status", OrdersState.SERVICING.getCode());
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 按订单id查询订单
     *
     * @param id 订单id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #id.toString()")
    public OrdersVO findById(long id) {
        OrdersVO orders = this.baseMapper.detail(id);
        if (orders == null) throw new BusinessException(0, "没有该订单");
        return orders;
    }

    /**
     * 查询全部未接单订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #query.toString()")
    public IPage<OrdersVO> findByReceive(Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .isNull("orders.status");
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 根据条件搜索全部未接单的订单（分页）
     *
     * @param option 分页参数
     * @param cid    客户用户id
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #query.toString()")
    public IPage<OrdersVO> searchByReceive(String start, String end, long businessId, String address, Query query) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.status", OrdersState.HAVE.getCode());
        if (start != null && end != null) {
            wrapper.between("orders.date", start, end);
        }
        if (businessId != 0) {
            wrapper.eq("store.business_id", businessId);
        }
        if (address != null) {
            wrapper.like("orders.address", address);
        }
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 查询全部已接单订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #query.toString()")
    public IPage<OrdersVO> findByCompleted(Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.status", OrdersState.SERVICING.getCode());
        return this.pageByWrapper(query, wrapper);
    }

    /**
     * 查询全部配送中订单
     *
     * @param option 分页参数
     * @return 分页包装数据
     */
    @Override
    @Cacheable(value = "order", key = "methodName + #query.toString()")
    public IPage<OrdersVO> findByPresent(Query query) {
        Wrapper<Orders> wrapper = new QueryWrapper<Orders>()
                .eq("orders.status", OrdersState.SERVICING.getCode());
        return this.pageByWrapper(query, wrapper);
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
        boolean status = userService.reduceScore(userId);
        redisService.del("order_" + id);
        // 下单1小时后自动更新为完成状态
        redisService.expire("completed_" + id, "expire", 1, TimeUnit.HOURS);
        return this.updateById(orders) && status;
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
        BusinessException.check(productService.addSales(orders.getPid(), 1), "增加产品销量失败");
        orders.setStatus(OrdersState.COMPLETE.getCode());
        return this.updateById(orders);
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
        orders.setStatus(OrdersState.COMPLETE.getCode());
        this.updateById(orders);
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