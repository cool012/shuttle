package com.example.hope.service;

import com.example.hope.config.BusinessException;
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
        int res = userMapper.insert(user);
        log.info("user insert -> " + user.toString() + " -> res -> " + res);
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
     * 更新用户
     * @param user
     */
    public void update(User user) {
        int res = userMapper.update(user);
        log.info("user update -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public List<User> findUserById(long id) {
        return userMapper.findUserById(id);
    }

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
     * @param encryption_password
     * @return
     */
    public int login(String username,String encryption_password){
        return userMapper.login(username,encryption_password);
    }
}
