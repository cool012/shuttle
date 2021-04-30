package com.example.hope.service;

import com.example.hope.model.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface UserService {

    void register(User user);

    Map<String, Object> login(String account, String password, int expired);

    void delete(long id);

    void update(User user, String token);

    void updatePassword(long id, String password, String token);

    void addScore(long id, int quantity);

    void reduceScore(long id);

    int findByScore(long id);

    List<User> findByPhone(String phone);

    List<User> findById(long id);

    PageInfo<User> findAll(Map<String, String> option);

    SearchHits search(String keyword, Map<String, String> option);

    void admin(long userId);

    boolean exist(long userId);

    User check(String token);

    void forget(String token, String newPassword);

    void sendEmail(String email);
}