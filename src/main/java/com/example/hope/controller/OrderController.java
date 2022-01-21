package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Orders;
import com.example.hope.service.business.OrderService;
import com.example.hope.service.serviceIpm.OrderServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 订单相关路由
 * @author: DHY
 * @created: 2020/10/25 14:20
 */
@RestController
@RequestMapping("/orders")
@Api(tags = "订单相关接口")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderServiceIpm orderService) {
        this.orderService = orderService;
    }

    @LoginUser
    @ApiOperation("添加订单")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(@RequestBody List<Orders> orderList, @RequestParam(defaultValue = "false") boolean isExpired) {
        orderService.insert(orderList, isExpired);
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(@RequestBody List<Orders> orders, HttpServletRequest request) {
        orderService.delete(orders, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("修改订单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Orders order) {
        orderService.update(order);
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("服务员接单")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public ReturnMessage<Object> receive(long orderId, long userId) {
        return ReturnMessageUtil.status(orderService.receive(orderId, userId));
    }

    @Admin
    @ApiOperation("查询全部订单")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Query query) {
        return ReturnMessageUtil.success(orderService.selectByPage(query));
    }

    @Admin
    @ApiOperation("根据pid查询订单")
    @RequestMapping(value = "/findByPid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPid(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findByPid(id, query));
    }

    @LoginUser
    @ApiOperation("根据cid查询全部的订单")
    @RequestMapping(value = "/findByCid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCid(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findByCid(id, query));
    }

    @LoginUser
    @ApiOperation("根据cid查询未下单的订单")
    @RequestMapping(value = "/findByCidOrOrder/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrOrder(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findByCidOrOrder(id, query));
    }

    @LoginUser
    @ApiOperation("根据cid查询配送中的订单")
    @RequestMapping(value = "/findByCidOrPresent/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrPresent(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findByCidOrPresent(id, query));
    }

    @LoginUser
    @ApiOperation("根据cid查询已完成的订单")
    @RequestMapping(value = "/findByCidOrCompleted/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrCompleted(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findByCidOrCompleted(id, query));
    }

    @LoginUser
    @ApiOperation("查询用户完成的订单")
    @RequestMapping(value = "/findBySidOrCompleted/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findBySidOrCompleted(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findBySidOrCompleted(id, query));
    }

    @LoginUser
    @ApiOperation("查询用户配送中的订单")
    @RequestMapping(value = "/findBySidOrPresent/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findBySidOrPresent(Query query, @PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findBySidOrPresent(id, query));
    }

    @LoginUser
    @ApiOperation("按订单id查询订单")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable long id) {
        return ReturnMessageUtil.success(orderService.findById(id));
    }

    @LoginUser
    @ApiOperation("完成订单")
    @RequestMapping(value = "/completed", method = RequestMethod.POST)
    public ReturnMessage<Object> completed(HttpServletRequest request, Orders orders) {
        return ReturnMessageUtil.status(orderService.completed(orders, request.getHeader("Authorization")));
    }

    @LoginUser
    @ApiOperation("查询全部未接单订单")
    @RequestMapping(value = "/findByReceive", method = RequestMethod.GET)
    public ReturnMessage<Object> findByReceive(Query query) {
        return ReturnMessageUtil.success(orderService.findByReceive(query));
    }

    @LoginUser
    @ApiOperation("查询全部完成订单")
    @RequestMapping(value = "/findByCompleted", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCompleted(Query query) {
        return ReturnMessageUtil.success(orderService.findByCompleted(query));
    }

    @LoginUser
    @ApiOperation("查询全部配送订单")
    @RequestMapping(value = "/findByPresent", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPresent(Query query) {
        return ReturnMessageUtil.success(orderService.findByPresent(query));
    }

    @LoginUser
    @ApiOperation("根据条件搜索cid全部的订单")
    @RequestMapping(value = "/searchByCid/{userId}", method = RequestMethod.GET)
    public ReturnMessage<Object> searchByCid(Query query, @RequestParam String start, @RequestParam String end,
                                             @RequestParam Long productId, @RequestParam Long serverId,
                                             @RequestParam int status, @PathVariable long userId) {
        return ReturnMessageUtil.success(orderService.searchByCid(userId, start, end, productId, serverId, status, query));
    }


    @LoginUser
    @ApiOperation("根据条件搜索全部未接单的订单")
    @RequestMapping(value = "/searchByReceive", method = RequestMethod.GET)
    public ReturnMessage<Object> searchByReceive(Query query, @RequestParam String start, @RequestParam String end,
                                                 @RequestParam long serviceId, @RequestParam String address) {
        return ReturnMessageUtil.success(orderService.searchByReceive(start, end, serviceId, address, query));
    }
}