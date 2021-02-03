package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.StoreMapper;
import com.example.hope.service.StoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

/**
 * @description: 商店服务实现类
 * @author: DHY
 * @created: 2021/02/03 19:48
 */
@Service
@Log4j2
public class StoreServiceImp implements StoreService {

    private StoreMapper storeMapper;

    @Autowired
    StoreServiceImp(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
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
    @Cacheable(value = "user", key = "methodName")
    public List<Store> findAll() {
        return storeMapper.findAll();
    }

    /**
     * 根据serviceId查询商店
     * @param serviceId
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #serviceId")
    public List<Store> findByServiceId(long serviceId) {
        return storeMapper.findByServiceId(serviceId, "serviceId");
    }

    /**
     * 根据categoryId查询商店
     * @param categoryId
     * @return
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #categoryId")
    public List<Store> findByCategoryId(long categoryId) {
        return storeMapper.findByCategoryId(categoryId,"categoryId");
    }
}
