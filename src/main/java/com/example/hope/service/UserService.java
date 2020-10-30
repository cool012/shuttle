package com.example.hope.service;

import com.example.hope.model.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void register(User user);

    String login(String email, String password, int expired);

    void resetPassword(String token, String password);

    void sendEmail(String email);

    void delete(long id);

    void update(User user);

    void addScore(long id, int quantity);

    void reduceScore(long id);

    int findByScore(long id);

    User findByEmail(String email);

    User findUserById(long id);

    List<User> findAll(Map<String,String> option);
}