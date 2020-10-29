package com.example.hope.service;

import com.example.hope.model.entity.Service;

import java.util.List;

public interface ServiceService {

    void insert(String serviceName);

    void delete(Long id);

    List<Service> findAll();
}
