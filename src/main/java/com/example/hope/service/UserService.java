package com.example.hope.service;

import com.example.hope.common.utils.Encoder;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.MailUtils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.User;
import com.example.hope.model.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户相关服务
 * @author: DHY
 * @created: 2020/10/23 19:56
 */
@Log4j2
@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    public void register(User user) {
        // 用户密码加密
        user.setPassword(Encoder.encode(user.getPassword()));
        int res = userMapper.insert(user);
        log.info("user register -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "注册失败");
    }

    /**
     * 删除用户
     *
     * @param id
     */
    public void delete(long id) {
        int res = userMapper.delete(id);
        log.info("user delete -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    public void update(User user) {
        int res = userMapper.update(user);
        log.info("user update -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 用户修改密码
     *
     * @param user
     */
    public void updatePassword(User user) {
        user.setPassword(Encoder.encode(user.getPassword()));
        int res = userMapper.updatePassword(user);
        log.info("user update password -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "修改密码失败");
    }

    /**
     * 重置密码 -> 输入邮箱，点击发送邮件 -> 根据user加密生成token -> 从邮箱访问 /reset/{token} （前端） ->
     * 前端获取url中token -> 重置密码（输入新密码） -> /user/restPassword
     *
     * @param password
     */
    public void resetPassword(String token, String password) {
        // 检查token是否有效
        long id = checkReset(token);
        // 重置密码
        updatePassword(new User(id, password));
    }

    /**
     * 发送邮箱
     *
     * @param email
     */
    //TODO 邮箱设置唯一约束
    public void sendEmail(String email) {
        User user = findByEmail(email);
        // 检查邮箱存不存在
        BusinessException.check(user == null ? 0 : 1, "用户不存在");
        // 加密生成邮箱token
        String token = JwtUtils.createToken(user, 60);
        // 发送邮箱
        mailUtils.sendTokenMail(email, token);
    }

    /**
     * 检查能不能重置
     *
     * @param token
     * @return
     */
    public long checkReset(String token) {
        long id = JwtUtils.getUserId(token);
        User user = findUserById(id);
        BusinessException.check(user == null ? 0 : 1, "用户不存在");
        return id;
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email
     */
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    public User findUserById(long id) {
        return userMapper.findUserById(id);
    }

    //TODO 缓存

    /**
     * 查询全部用户
     *
     * @return
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param expired  token过期时间，单位：分钟
     * @return
     */
    public String login(String username, String password, int expired) {
        String encryption_password = Encoder.encode(password);
        User user = userMapper.login(username, encryption_password);
        BusinessException.check(user != null ? 1 : 0, "登录失败，用户名或密码错误");
        return JwtUtils.createToken(user, expired);
    }

    /**
     * 按名字查询用户
     *
     * @param name
     * @return
     */
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}