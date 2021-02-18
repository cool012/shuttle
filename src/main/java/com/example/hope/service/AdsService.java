package com.example.hope.service;

import com.example.hope.model.entity.Ads;

import java.util.List;

public interface AdsService {

    void insert(Ads ads, int expired);

    void delete(long id);

    void update(Ads ads);

    List<Ads> findAll();
}
