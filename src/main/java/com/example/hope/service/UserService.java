package com.example.hope.service;

import com.example.hope.model.entity.User;

import java.util.List;

public interface UserService {

    void register(User user);

    String login(String email, String password, int expired);

    void delete(long id);

    void update(User user);

    void addScore(long id, int quantity);

    void reduceScore(long id);

    void resetPassword(String token, String password);

    void sendEmail(String email);

    User findByEmail(String email);

    User findUserById(long id);

    List<User> findAll();

    int findByScore(long id);
}
