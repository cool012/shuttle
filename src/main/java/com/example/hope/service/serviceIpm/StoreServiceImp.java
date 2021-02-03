package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.StoreMapper;
import com.example.hope.service.StoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description: 商店服务实现类
 * @author: DHY
 * @created: 2021/02/03 19:48
 */
@Service
@Log4j2
public class StoreServiceImp implements StoreService {

    private StoreMapper storeMapper;
    private RedisUtil redisUtil;

    @Autowired
    StoreServiceImp(StoreMapper storeMapper, RedisUtil redisUtil) {
        this.storeMapper = storeMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加商店
     *
     * @param store
     */
    @Override
    @Transient
    public void insert(Store store) {
        int res = storeMapper.insert(store);
        log.info("store insert -> " + store.toString() + " -> res -> " + res);
        BusinessException.check(res, "添加失败");
    }

    /**
     * 删除商店
     *
     * @param id
     */
    @Override
    @Transient
    public void delete(long id) {
        int res = storeMapper.delete(id);
        log.info("store delete -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新商店
     *
     * @param store
     */
    @Override
    @Transient
    public void update(Store store) {
        int res = storeMapper.update(store);
        log.info("store update -> " + store.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部商店
     *
     * @return
     */
    @Override
    @Cacheable(value = "store", key = "methodName")
    public List<Store> findAll() {
        return storeMapper.findAll();
    }

    /**
     * 根据serviceId查询商店
     *
     * @param serviceId
     * @return
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #serviceId")
    public List<Store> findByServiceId(long serviceId) {
        return storeMapper.findByServiceId(serviceId, "serviceId");
    }

    /**
     * 根据categoryId查询商店
     *
     * @param categoryId
     * @return
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #categoryId")
    public List<Store> findByCategoryId(long categoryId) {
        return storeMapper.findByCategoryId(categoryId, "categoryId");
    }

    @Override
    @Cacheable(value = "store", key = "methodName + #id")
    public Store findById(long id) {
        return storeMapper.findById(id, "id");
    }

    @Override
    public List<Store> range() {
        Set<String> range = redisUtil.range("rank", 0, -1);
        List<Store> stores = new ArrayList<>();
        for (String id : range) {
            stores.add(findById(Long.valueOf(id)));
        }
        return stores;
    }

    /**
     * 增加商店销量
     * @param id
     * @param quantity
     */
    @Override
    public void sales(long id, int quantity) {
        storeMapper.sales(id, quantity);
        redisUtil.incrScore("rank", String.valueOf(id), Double.valueOf(quantity));
    }
}
