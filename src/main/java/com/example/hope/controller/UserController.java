package com.example.hope.controller;

import com.alipay.api.AlipayApiException;
import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.User;
import com.example.hope.service.PayService;
import com.example.hope.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 用户相关路由
 * @author: DHY
 * @created: 2020/10/23 20:11
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private PayService payService;

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnMessage<Object> login(String phone, String password, int expired) {
        return ReturnMessageUtil.sucess(userService.login(phone, password, expired));
    }

    @LoginUser
    @ApiOperation("检查token")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ReturnMessage<Object> check() {
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnMessage<Object> register(User user) {
        userService.register(user);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ReturnMessage<Object> resetPassword(long id, String password) {
        userService.updatePassword(id, password);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("用户删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> login(long id) {
        userService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(com.example.hope.model.entity.User user) {
        userService.update(user);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("根据id查询用户")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.sucess(userService.findById(id).get(0));
    }

    @Admin
    @ApiOperation("查询全部用户")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(userService.findAll(option));
    }

    @Admin
    @ApiOperation("根据手机号查询用户")
    @RequestMapping(value = "/findByPhone/{phone}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPhone(@PathVariable("phone") String phone) {
        return ReturnMessageUtil.sucess(userService.findByPhone(phone).get(0));
    }

    @LoginUser
    @ApiOperation("查询点数")
    @RequestMapping(value = "/findSore/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findScore(@PathVariable("id") long id) {
        int score = userService.findByScore(id);
        return ReturnMessageUtil.sucess(score);
    }

    @ApiOperation("充值")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public String recharge(long userId, int total) throws AlipayApiException {
        return payService.alipay(userId, total);
    }

    @Admin
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(userService.search(keyword, option));
    }

    @Admin
    @ApiOperation("设置管理员")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ReturnMessage<Object> admin(long userId) {
        userService.admin(userId);
        return ReturnMessageUtil.sucess();
    }
}