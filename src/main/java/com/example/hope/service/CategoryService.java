package com.example.hope.service;

import com.example.hope.model.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    void insert(Category category);

    void delete(long id);

    void update(Category category);

    PageInfo<Category> findAll(Map<String, String> option);

    List<Category> findAllByServiceId(long serviceId);

    boolean exist(long id);

    List<Category> findById(long id);
}
