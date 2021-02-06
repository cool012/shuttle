package com.example.hope.service;

import com.example.hope.model.entity.Service;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ServiceService {

    void insert(Service service);

    void delete(Long id);

    void update(Service service);

    PageInfo<Service> findAll(Map<String, String> option);
}
