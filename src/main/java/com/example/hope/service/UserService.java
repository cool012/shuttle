package com.example.hope.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User> {

    boolean register(User user);

    Map<String, Object> login(String account, String password, int expired);

    boolean delete(long id);

    boolean update(User user, String token);

    boolean updatePassword(long id, String password, String token);

    boolean addScore(long id, int quantity);

    boolean reduceScore(long id);

    int findByScore(long id);

    User findByPhone(String phone);

    User findById(long id);

    PageInfo<User> findAll(Map<String, String> option);

    SearchHits search(String keyword, Map<String, String> option);

    boolean admin(long userId);

    boolean exist(long userId);

    User check(String token);

    void forget(String token, String newPassword);

    void sendEmail(String email);
}