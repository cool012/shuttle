package com.example.hope.controller;

import com.alipay.api.AlipayApiException;
import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.annotation.WebLog;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.User;
import com.example.hope.service.other.PayService;
import com.example.hope.service.business.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @WebLog(description = "'用户 ' + #account + ' 登录系统'")
    public ReturnMessage<Object> login(String account, String password, int expired) {
        return ReturnMessageUtil.success(userService.login(account, password, expired));
    }

    @LoginUser
    @ApiOperation("检查token")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ReturnMessage<Object> check(HttpServletRequest request) {
        return ReturnMessageUtil.success(userService.check(request.getHeader("Authorization")));
    }

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @WebLog(description = "'用户 ' + #user.name + ' 注册成功'")
    public ReturnMessage<Object> register(User user) {
        return ReturnMessageUtil.status(userService.register(user));
    }

    @LoginUser
    @ApiOperation("重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @WebLog(description = "'用户 ' + #id + ' 重置密码成功'")
    public ReturnMessage<Object> resetPassword(long id, String password, HttpServletRequest request) {
        return ReturnMessageUtil.status(userService.updatePassword(id, password, request.getHeader("Authorization")));
    }

    @Admin
    @ApiOperation("用户删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @WebLog(description = "'删除 ' + #id + ' 用户成功'")
    public ReturnMessage<Object> login(long id) {
        userService.delete(id);
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @WebLog(description = "'更新 ' + #user.id + ' 用户信息成功'")
    public ReturnMessage<Object> update(User user, HttpServletRequest request) {
        userService.update(user, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("根据id查询用户")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.success(userService.findById(id));
    }

    @Admin
    @ApiOperation("查询全部用户")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Query query) {
        return ReturnMessageUtil.success(userService.findAll(query));
    }

    @Admin
    @ApiOperation("根据手机号查询用户")
    @RequestMapping(value = "/findByPhone/{phone}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPhone(@PathVariable("phone") String phone) {
        return ReturnMessageUtil.success(userService.findByPhone(phone));
    }

    @LoginUser
    @ApiOperation("查询点数")
    @RequestMapping(value = "/findSore/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findScore(@PathVariable("id") long id) {
        int score = userService.findByScore(id);
        return ReturnMessageUtil.success(score);
    }

    @ApiOperation("充值")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @WebLog(description = "'用户 ' + #userId + ' 充值 ' + #total +' 成功'")
    public String recharge(long userId, int total) throws AlipayApiException {
        return payService.alipay(userId, total);
    }

    @Admin
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(userService.search(keyword, option));
    }

    @Admin
    @ApiOperation("设置管理员")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    @WebLog(description = "'设置用户 ' + #userId + ' 为管理员成功'")
    public ReturnMessage<Object> admin(long userId) {
        userService.admin(userId);
        return ReturnMessageUtil.success();
    }

    @ApiOperation("发送邮箱")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public ReturnMessage<Object> sendEmail(String email) {
        userService.sendEmail(email);
        return ReturnMessageUtil.success();
    }

    @ApiOperation("忘记密码")
    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    @WebLog(description = "用户忘记密码")
    public ReturnMessage<Object> forget(String token, String newPassword) {
        return ReturnMessageUtil.status(userService.forget(token, newPassword));
    }
}