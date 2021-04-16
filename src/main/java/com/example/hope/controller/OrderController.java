package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Orders;
import com.example.hope.service.OrderService;
import com.example.hope.service.serviceIpm.OrderServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单相关路由
 * @author: DHY
 * @created: 2020/10/25 14:20
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单相关接口")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderServiceIpm orderService) {
        this.orderService = orderService;
    }

    /**
     * showdoc
     *
     * @param orders.cid     必选 long 订单用户id
     * @param orders.sid     必选 long 订单服务id
     * @param orders.pid     必选 long 订单产品id
     * @param orders.date    必选 string 订单时间
     * @param orders.address 必选 string 订单地址
     * @param orders.note    必选 string 订单备注
     * @param orders.file    必选 string 订单文件
     * @param orders.status  必选 string 订单状态
     * @param isExpired      可选 bool 是否设置超时（15分钟）
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 订单
     * @title 添加
     * @description 添加订单的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /orders/insert
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("添加订单")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(@RequestBody List<Orders> orderList, @RequestParam(defaultValue = "false") boolean isExpired) {
        orderService.insert(orderList, isExpired);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param orders.cid     必选 long 订单用户id
     * @param orders.sid     必选 long 订单服务id
     * @param orders.pid     必选 long 订单产品id
     * @param orders.date    必选 string 订单时间
     * @param orders.address 必选 string 订单地址
     * @param orders.note    必选 string 订单备注
     * @param orders.file    必选 string 订单文件
     * @param orders.status  必选 string 订单状态
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 订单
     * @title 删除
     * @description 删除订单的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /orders/delete
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(@RequestBody List<Orders> orders, HttpServletRequest request) {
        orderService.delete(orders, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param orders.cid     必选 long 订单用户id
     * @param orders.sid     必选 long 订单服务id
     * @param orders.pid     必选 long 订单产品id
     * @param orders.date    必选 string 订单时间
     * @param orders.address 必选 string 订单地址
     * @param orders.note    必选 string 订单备注
     * @param orders.file    必选 string 订单文件
     * @param orders.status  必选 string 订单状态
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 订单
     * @title 修改
     * @description 修改订单的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /orders/update
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("修改订单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Orders order) {
        orderService.update(order);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param ordersId 必选 long 订单id
     * @param userId   必选 long 用户id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 订单
     * @title 接单
     * @description 接单的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /orders/receive
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("服务员接单")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public ReturnMessage<Object> receive(long orderId, long userId) {
        orderService.receive(orderId, userId);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 修改
     * @description 修改订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findAll
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("查询全部订单")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findAll(option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 产品id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 根据pid查询订单
     * @description 根据pid查询订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findByPid/{id}
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("根据pid查询订单")
    @RequestMapping(value = "/findByPid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPid(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByPid(id, option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 用户id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 根据cid查询订单
     * @description 根据cid查询订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findByCid/{id}
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("根据cid查询全部的订单")
    @RequestMapping(value = "/findByCid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCid(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCid(id, option));
    }

    @LoginUser
    @ApiOperation("根据cid查询未下单的订单")
    @RequestMapping(value = "/findByCidOrOrder/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrOrder(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCidOrOrder(id, option));
    }

    @LoginUser
    @ApiOperation("根据cid查询配送中的订单")
    @RequestMapping(value = "/findByCidOrPresent/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrPresent(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCidOrPresent(id, option));
    }

    @LoginUser
    @ApiOperation("根据cid查询已完成的订单")
    @RequestMapping(value = "/findByCidOrCompleted/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCidOrCompleted(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCidOrCompleted(id, option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 服务id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 查询用户完成的订单
     * @description 查询用户完成的订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findBySidOrCompleted/{id}
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询用户完成的订单")
    @RequestMapping(value = "/findBySidOrCompleted/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findBySidOrCompleted(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findBySidOrCompleted(id, option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 服务id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 查询用户配送中的订单
     * @description 查询用户配送中的订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findBySidOrPresent/{id}
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询用户配送中的订单")
    @RequestMapping(value = "/findBySidOrPresent/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findBySidOrPresent(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findBySidOrPresent(id, option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 订单id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "order"}
     * @catalog 订单
     * @title 按订单id查询订单
     * @description 按订单id查询订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findById/{id}
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("按订单id查询订单")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable long id) {
        return ReturnMessageUtil.sucess(orderService.findById(id));
    }

    /**
     * showdoc
     *
     * @param orders.cid     必选 long 订单用户id
     * @param orders.sid     必选 long 订单服务id
     * @param orders.pid     必选 long 订单产品id
     * @param orders.date    必选 string 订单时间
     * @param orders.address 必选 string 订单地址
     * @param orders.note    必选 string 订单备注
     * @param orders.file    必选 string 订单文件
     * @param orders.status  必选 string 订单状态
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 订单
     * @title 完成订单
     * @description 完成订单的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /orders/completed
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("完成订单")
    @RequestMapping(value = "/completed", method = RequestMethod.POST)
    public ReturnMessage<Object> completed(HttpServletRequest request, Orders orders) {
        orderService.completed(orders, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 查询全部未接单订单
     * @description 查询全部未接单订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findByReceive
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询全部未接单订单")
    @RequestMapping(value = "/findByReceive", method = RequestMethod.GET)
    public ReturnMessage<Object> findByReceive(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByReceive(option));
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 查询全部完成订单
     * @description 查询全部完成订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findByCompleted
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询全部完成订单")
    @RequestMapping(value = "/findByCompleted", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCompleted(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCompleted(option));
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "orders"}
     * @catalog 订单
     * @title 查询全部配送订单
     * @description 查询全部配送订单的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /orders/findByPresent
     * @return_param orders.cid 必选 long 订单用户id
     * @return_param orders.sid 必选 long 订单服务id
     * @return_param orders.pid 必选 long 订单产品id
     * @return_param orders.date 必选 string 订单时间
     * @return_param orders.address 必选 string 订单地址
     * @return_param orders.note 必选 string 订单备注
     * @return_param orders.file 必选 string 订单文件
     * @return_param orders.status 必选 string 订单状态
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询全部配送订单")
    @RequestMapping(value = "/findByPresent", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPresent(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByPresent(option));
    }
}