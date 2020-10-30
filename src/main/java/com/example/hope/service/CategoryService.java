package com.example.hope.service;

import com.example.hope.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    int insert(Category category);

    int delete(long id);

    int update(Category category);

    List<Category> findAll();

    List<Category> findAllByServiceId(long serviceId);
}
