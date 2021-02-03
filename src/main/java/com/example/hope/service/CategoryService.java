package com.example.hope.service;

import com.example.hope.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    void insert(Category category);

    void delete(long id);

    void update(Category category);

    List<Category> findAll();

    List<Category> findAllByServiceId(long serviceId);
}
