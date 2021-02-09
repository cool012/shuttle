package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.*;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.User;
import com.example.hope.model.mapper.UserMapper;
import com.example.hope.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户相关服务
 * @author: DHY
 * @created: 2020/10/23 19:56
 */
@Log4j2
@Service
public class UserServiceIpm implements UserService {

    private UserMapper userMapper;

    @Autowired
    UserServiceIpm(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @Override
    @Transient
    @CacheEvict(value = "user", allEntries = true)
    public void register(User user) {
        System.out.println(user);
        // 检查输入合法
        Utils.check_user(user);
        // 用户密码加密
        user.setPassword(Utils.encode(user.getPassword()));
        int res = userMapper.insert(user);
        log.info("user register -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "注册失败");
    }

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @param expired  token过期时间，单位：分钟
     * @return
     */
    @Override
    public Map<String, Object> login(String phone, String password, int expired) {
        String encryption_password = Utils.encode(password);
        User user = userMapper.login(phone, encryption_password);
        BusinessException.check(user != null ? 1 : 0, "登录失败，用户名或密码错误");
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtUtils.createToken(user, expired));
        map.put("user", user);
        return map;
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    @Transient
    @CacheEvict(value = "user", allEntries = true)
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
    @Override
    @Transient
    @CacheEvict(value = "user", allEntries = true)
    public void update(User user) {
        int res = userMapper.update(user);
        log.info("user update -> " + user.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 用户修改密码
     *
     * @param id
     * @param password
     */
    @Transient
    @CacheEvict(value = "user", allEntries = true)
    public void updatePassword(long id, String password) {
        int res = userMapper.updatePassword(id, Utils.encode(password));
        log.info("user update password ->" + id, password + " -> res -> " + res);
        BusinessException.check(res, "修改密码失败");
    }

    /**
     * 增加点数
     *
     * @param id
     * @param quantity 数量
     */
    @Override
    @Transient
    @CacheEvict(value = "user", allEntries = true)
    public void addScore(long id, int quantity) {
        int res = userMapper.addScore(id, quantity);
        log.info("user addScore -> userId:" + id + " -> quantity:" + quantity + " -> res -> " + res);
        BusinessException.check(res, "增加点数失败");
    }

    /**
     * 点数减1
     *
     * @param id
     */
    @Override
    @Transient
    @CacheEvict(value = "user", allEntries = true)
    public void reduceScore(long id) {
        if (findByScore(id) == 0) {
            throw new BusinessException(-1, "用户点数为0");
        }
        int res = userMapper.reduceScore(id);
        log.info("user reduceScore -> " + id + " -> res -> " + res);
        BusinessException.check(res, "减少点数失败");
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #phone")
    public List<User> findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #id")
    public List<User> findById(long id) {
        return userMapper.findUserById(id);
    }

    /**
     * 查询全部用户
     *
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #option.toString()")
    public PageInfo<User> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(userMapper.findAll());
    }


    /**
     * 查询用户点数
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #id")
    public int findByScore(long id) {
        return userMapper.findByScore(id);
    }

    /**
     * 搜索
     *
     * @param keyword
     * @param option
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #keyword")
    public PageInfo<User> search(String keyword, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(userMapper.search("%" + keyword + "%"));
    }

    /**
     * 设置管理员
     *
     * @param userId
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void admin(long userId) {
        int res = userMapper.admin(userId);
        log.info("user update admin -> " + userId + " -> res -> " + res);
        BusinessException.check(res, "设置管理员失败");
    }
}