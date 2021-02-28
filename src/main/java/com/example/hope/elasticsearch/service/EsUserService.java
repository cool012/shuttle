package com.example.hope.elasticsearch.service;

import com.example.hope.model.entity.User;

import java.util.List;

public interface EsUserService {

    void importAll();

    void save(User user);

    void delete(long productId);

    List<User> search(String keyword);
}
