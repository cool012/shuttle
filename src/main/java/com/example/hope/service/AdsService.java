package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Ads;

import java.util.List;

public interface AdsService extends BaseService<Ads> {

    boolean insert(Ads ads, int expired);

    boolean delete(long id);

    boolean update(Ads ads);

    List<Ads> findAll();
}
