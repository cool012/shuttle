package com.example.hope.service.serviceIpm;

import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.config.redis.RedisService;
import com.example.hope.model.entity.Ads;
import com.example.hope.model.mapper.AdsMapper;
import com.example.hope.service.AdsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 广告服务实体类
 * @author: DHY
 * @created: 2021/02/18 17:19
 */
@Log4j2
@Service
public class AdsServiceImp extends BaseServiceImp<Ads, AdsMapper> implements AdsService {

    @Resource
    private RedisService redisService;

    /**
     * 添加广告
     *
     * @param ads     广告
     * @param expired 过期时间（单位：天）
     */
    @Override
    @Transactional
    @CacheEvict(value = "ads", allEntries = true)
    public boolean insert(Ads ads, int expired) {
        boolean state = this.save(ads);
        // 设置过期时间
        if (state) redisService.expire("ads_" + ads.getId(), "expire", expired, TimeUnit.DAYS);
        return state;
    }

    /**
     * 删除广告
     *
     * @param id 广告id
     */
    @Override
    @Transactional
    @CacheEvict(value = "ads", allEntries = true)
    public boolean delete(long id) {
        return this.removeById(id);
    }

    /**
     * 根据商店id删除广告
     *
     * @param storeId 商店id
     */
    @Transactional
    @CacheEvict(value = "ads", allEntries = true)
    public boolean deleteByStoreId(long storeId) {
        return this.remove(this.getQueryWrapper(Ads::getStoreId, storeId));
    }

    /**
     * 更新广告
     *
     * @param ads 广告
     */
    @Override
    @Transactional
    @CacheEvict(value = "ads", allEntries = true)
    public boolean update(Ads ads) {
        return this.updateById(ads);
    }

    /**
     * 查询全部广告
     *
     * @return 广告列表
     */
    @Override
    @Cacheable(value = "ads", key = "methodName")
    public List<Ads> findAll() {
        return this.list();
    }
}
