package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Ads;
import com.example.hope.model.mapper.AdsMapper;
import com.example.hope.service.AdsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 广告服务实体类
 * @author: DHY
 * @created: 2021/02/18 17:19
 */
@Log4j2
@Service
public class AdsServiceImp implements AdsService {

    private AdsMapper adsMapper;
    private RedisUtil redisUtil;

    @Autowired
    public AdsServiceImp(AdsMapper adsMapper, RedisUtil redisUtil) {
        this.adsMapper = adsMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加广告
     *
     * @param ads 广告
     * @param expired 过期时间（单位：天）
     */
    @Override
    @Transient
    @CacheEvict(value = "ads", allEntries = true)
    public void insert(Ads ads, int expired) {
        int res = adsMapper.insert(ads);
        // 设置过期时间
        if (res > 0) redisUtil.ins("ads_" + ads.getId(), "expired", expired, TimeUnit.DAYS);
        log.info("ads insert -> " + ads.toString() + " -> res -> " + res);
        BusinessException.check(res, "添加失败");
    }

    /**
     * 删除广告
     *
     * @param id 广告id
     */
    @Override
    @Transient
    @CacheEvict(value = "ads", allEntries = true)
    public void delete(long id) {
        int res = adsMapper.delete(id);
        log.info("ads delete -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新广告
     *
     * @param ads 广告
     */
    @Override
    @Transient
    @CacheEvict(value = "ads", allEntries = true)
    public void update(Ads ads) {
        int res = adsMapper.update(ads);
        log.info("ads delete -> " + ads.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部广告
     *
     * @return 广告列表
     */
    @Override
    @Cacheable(value = "ads", key = "methodName")
    public List<Ads> findAll() {
        return adsMapper.select();
    }
}
