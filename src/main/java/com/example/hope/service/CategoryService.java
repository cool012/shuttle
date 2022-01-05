package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CategoryService extends BaseService<Category> {

    boolean insert(Category category);

    boolean delete(long id);

    boolean deleteByServiceId(long serviceId);

    boolean update(Category category);

    PageInfo<Category> page(Map<String, String> option);

    List<Category> findAllByServiceId(long serviceId);

    boolean exist(long id);

    Category detail(long id);
}
