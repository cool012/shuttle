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

    /**
     * showdoc
     *
     * @param phone    必选 string 账户（手机号/昵称）
     * @param password 必选 string 密码
     * @param expired  必选 int 过期时间（分钟）
     * @return {"code": 1,"message": "success","data": { "user": "","token": "" } }
     * @catalog 用户
     * @title 登录
     * @description 用户登录的接口
     * @method post
     * @url /user/login
     * @return_param user.id int 用户id
     * @return_param user.password string 用户密码
     * @return_param user.phone string 用户电话号
     * @return_param user.address string 用户地址
     * @return_param user.score int 用户点数
     * @return_param user.admin bool 用户组
     * @return_param token name 昵称
     * @return_param token string token
     */
    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnMessage<Object> login(String account, String password, int expired) {
        return ReturnMessageUtil.success(userService.login(account, password, expired));
    }

    /**
     * showdoc
     *
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 用户
     * @title 检查token
     * @description 检查token的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/check
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("检查token")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ReturnMessage<Object> check(HttpServletRequest request) {
        return ReturnMessageUtil.success(userService.check(request.getHeader("Authorization")));
    }

    /**
     * showdoc
     *
     * @param user.phone    必选 string 用户电话号
     * @param user.password 必选 string 用户密码
     * @param user.address  必选 string 用户地址
     * @param user.name     必选 string 昵称
     * @return {"code": 1,"message": "success","data": "null" }
     * @catalog 用户
     * @title 注册
     * @description 用户注册的接口
     * @method post
     * @url /user/register
     */
    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnMessage<Object> register(User user) {
        userService.register(user);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param user.phone    必选 string 用户电话号
     * @param user.password 必选 string 用户密码
     * @param user.address  必选 string 用户地址
     * @param user.name     必选 string 昵称
     * @return {"code": 1,"message": "success","data": "null" }
     * @catalog 用户
     * @title 重置密码
     * @description 用户重置密码的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /user/resetPassword
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ReturnMessage<Object> resetPassword(long id, String password, HttpServletRequest request) {
        userService.updatePassword(id, password, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param id 必选 long 用户id
     * @return {"code": 1,"message": "success","data": "null" }
     * @catalog 用户
     * @title 删除
     * @description 用户删除的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /user/delete
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("用户删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> login(long id) {
        userService.delete(id);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param user.phone    必选 string 用户电话号
     * @param user.password 必选 string 用户密码
     * @param user.address  必选 string 用户地址
     * @param user.name     必选 string 昵称
     * @return {"code": 1,"message": "success","data": "null" }
     * @catalog 用户
     * @title 更新
     * @description 用户更新的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /user/update
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(User user, HttpServletRequest request) {
        userService.update(user,request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param id 必选 long 用户id
     * @return {"code": 1,"message": "success","data": "user" }
     * @catalog 用户
     * @title 根据id查询用户
     * @description 根据id查询用户的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/findById/{id}
     * @return_param user.id int 用户id
     * @return_param user.password string 用户密码
     * @return_param user.phone string 用户电话号
     * @return_param user.address string 用户地址
     * @return_param user.score int 用户点数
     * @return_param user.admin bool 用户组
     * @return_param token name 昵称
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("根据id查询用户")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.success(userService.findById(id).get(0));
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "users" }
     * @catalog 用户
     * @title 查询全部用户
     * @description 查询全部用户的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/findAll
     * @return_param total 总数
     * @return_param pageNum 当前页数
     * @return_param pageSize 每页的数据条数
     * @return_param user.id int 用户id
     * @return_param user.password string 用户密码
     * @return_param user.phone string 用户电话号
     * @return_param user.address string 用户地址
     * @return_param user.score int 用户点数
     * @return_param user.admin bool 用户组
     * @return_param token name 昵称
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("查询全部用户")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(userService.findAll(option));
    }

    /**
     * showdoc
     *
     * @param phone    必选 String 电话号
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "users" }
     * @catalog 用户
     * @title 根据手机号查询用户
     * @description 根据手机号查询用户的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/findByPhone/{phone}
     * @return_param total 总数
     * @return_param pageNum 当前页数
     * @return_param pageSize 每页的数据条数
     * @return_param user.id int 用户id
     * @return_param user.password string 用户密码
     * @return_param user.phone string 用户电话号
     * @return_param user.address string 用户地址
     * @return_param user.score int 用户点数
     * @return_param user.admin bool 用户组
     * @return_param token name 昵称
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("根据手机号查询用户")
    @RequestMapping(value = "/findByPhone/{phone}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPhone(@PathVariable("phone") String phone) {
        return ReturnMessageUtil.success(userService.findByPhone(phone).get(0));
    }

    /**
     * showdoc
     *
     * @param id 必选 int 用户id
     * @return {"code": 1,"message": "success","data": "score" }
     * @catalog 用户
     * @title 查询点数
     * @description 查询点数的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/findSore/{id}
     * @return_param score 点数
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询点数")
    @RequestMapping(value = "/findSore/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findScore(@PathVariable("id") long id) {
        int score = userService.findByScore(id);
        return ReturnMessageUtil.success(score);
    }

    /**
     * showdoc
     *
     * @param userId 必选 long 用户id
     * @param total  必选 int 充值的数量
     * @catalog 用户
     * @title 充值
     * @description 充值的接口
     * @method post
     * @url /user/recharge
     * @remark 跳转到支付宝支付界面
     */
    @ApiOperation("充值")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public String recharge(long userId, int total) throws AlipayApiException {
        return payService.alipay(userId, total);
    }

    /**
     * showdoc
     *
     * @param keyword  必选 string 关键词
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @return {"code": 1,"message": "success","data": "users" }
     * @catalog 用户
     * @title 搜索
     * @description 搜索用户的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /user/search/{keyword}
     * @return_param total 总数
     * @return_param pageNum 当前页数
     * @return_param pageSize 每页的数据条数
     * @return_param user.id int 用户id
     * @return_param user.password string 用户密码
     * @return_param user.phone string 用户电话号
     * @return_param user.address string 用户地址
     * @return_param user.score int 用户点数
     * @return_param user.admin bool 用户组
     * @return_param token name 昵称
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(userService.search(keyword, option));
    }

    /**
     * showdoc
     *
     * @param userId 必选 long 用户id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 用户
     * @title 设置管理员
     * @description 设置管理员的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /user/admin
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("设置管理员")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
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
    public ReturnMessage<Object> forget(String token, String newPassword) {
        userService.forget(token, newPassword);
        return ReturnMessageUtil.success();
    }
}