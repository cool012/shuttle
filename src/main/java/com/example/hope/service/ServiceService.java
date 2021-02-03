package com.example.hope.service;

import com.example.hope.model.entity.Service;

import java.util.List;

public interface ServiceService {

    void insert(Service service);

    void delete(Long id);

    void update(Service service);

    List<Service> findAll();
}
