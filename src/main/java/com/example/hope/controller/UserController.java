package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.User;
import com.example.hope.service.UserService;
import com.example.hope.service.serviceIpm.UserServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private UserService userService;

    @Autowired
    public UserController(UserServiceIpm userService) {
        this.userService = userService;
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnMessage<Object> login(String email, String password,int expired) {
        return ReturnMessageUtil.sucess(userService.login(email, password, expired));
    }

    @LoginUser
    @ApiOperation("检查token")
    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public ReturnMessage<Object> check(){
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnMessage<Object> register(User user) {
        userService.register(user);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("发送邮件")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public ReturnMessage<Object> sendEmail(String email) {
        userService.sendEmail(email);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ReturnMessage<Object> resetPassword(String token, String password) {
        userService.resetPassword(token, password);
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
    @RequestMapping(value = "/findUserById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findUserById(@PathVariable("id") long id) {
        return ReturnMessageUtil.sucess(userService.findUserById(id));
    }

    @Admin
    @ApiOperation("查询全部用户")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(userService.findAll(option));
    }

    @ApiOperation("根据邮箱查询用户")
    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET)
    public ReturnMessage<Object> findByEmail(String Email) {
        return ReturnMessageUtil.sucess(userService.findByEmail(Email));
    }

    @ApiOperation("增加点数")
    @RequestMapping(value = "/addScore", method = RequestMethod.POST)
    public ReturnMessage<Object> addScore(long id, int quantity) {
        userService.addScore(id, quantity);
        return ReturnMessageUtil.sucess();
    }

}
