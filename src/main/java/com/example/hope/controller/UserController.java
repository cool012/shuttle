package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.UserLoginToken;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.User;
import com.example.hope.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnMessage<Object> login(String username, String password, int expired) {
        String token = userService.login(username, password, expired);
        return ReturnMessageUtil.sucess(token);
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
    public ReturnMessage<Object> resetPassword(String token,String password) {
        userService.resetPassword(token,password);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("用户删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ReturnMessage<Object> login(@PathVariable("id") long id) {
        userService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @UserLoginToken
    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(User user) {
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
    public ReturnMessage<Object> findAll() {
        return ReturnMessageUtil.sucess(userService.findAll());
    }

    @ApiOperation("根据名字查询用户")
    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public ReturnMessage<Object> findByName(String username) {
        return ReturnMessageUtil.sucess(userService.findByName(username));
    }
}
