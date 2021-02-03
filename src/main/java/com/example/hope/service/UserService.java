package com.example.hope.service;

import com.example.hope.model.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void register(User user);

    Map<String, Object> login(String phone, String password, int expired);

    void delete(long id);

    void update(User user);

    void updatePassword(long id,String password);

    void addScore(long id, int quantity);

    void reduceScore(long id);

    int findByScore(long id);

    User findByPhone(String phone);

    User findUserById(long id);

    List<User> findAll(Map<String, String> option);
}