package com.example.hope.service;

import com.example.hope.common.utils.Encoder;
import com.example.hope.common.utils.JwtUtils;
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
    UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户注册
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
     * @param id
     */
    public void delete(long id) {
        int res = userMapper.delete(id);
        log.info("user delete -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 修改用户信息
     * @param user
     */
    public void update(User user) {
        int res = userMapper.update(user);
        log.info("user update -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    //TODO 修改用户密码

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findUserById(long id) {
        return userMapper.findUserById(id);
    }

    //TODO 缓存

    /**
     * 查询全部用户
     * @return
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 用户登录
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
     * @param name
     * @return
     */
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    //TODO 按id查询用户
}