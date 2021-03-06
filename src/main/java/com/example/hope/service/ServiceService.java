package com.example.hope.service;

import com.example.hope.model.entity.Services;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ServiceService {

    void insert(Services services);

    void delete(Long id);

    void update(Services services);

    PageInfo<Services> findAll(Map<String, String> option);

    List<Services> findById(long id);

    boolean exist(long id);
}
