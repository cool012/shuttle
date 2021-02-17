package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.StoreMapper;
import com.example.hope.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;

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
     * @param store 商店
     */
    @Override
    @Transient
    @CacheEvict(value = "store", allEntries = true)
    public void insert(Store store) {
        int res = storeMapper.insert(store);
        log.info("store insert -> " + store.toString() + " -> res -> " + res);
        BusinessException.check(res, "添加失败");
    }

    /**
     * 增加商店销量
     *
     * @param id       商店id
     * @param quantity 数量
     */
    @Override
    public void sales(long id, int quantity) {
        int res = storeMapper.sales(id, quantity);
        BusinessException.check(res, "增加商店销量失败");
        redisUtil.incrScore("store_rank", String.valueOf(id), quantity);
    }

    /**
     * 删除商店
     *
     * @param id 商店id
     */
    @Override
    @Transient
    @CacheEvict(value = "store", allEntries = true)
    public void delete(long id) {
        int res = storeMapper.delete(id);
        log.info("store delete -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新商店评分
     *
     * @param id   商店id
     * @param rate 评分
     */
    @Override
    public void review(long id, float rate) {
        int res = storeMapper.review(id, rate);
        log.info(id + " " + rate);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 更新商店
     *
     * @param store 商店
     */
    @Override
    @Transient
    @CacheEvict(value = "store", allEntries = true)
    public void update(Store store) {
        int res = storeMapper.update(store);
        log.info("store update -> " + store.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部商店
     *
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #option.toString()")
    public PageInfo<Store> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")));
        return PageInfo.of(storeMapper.select(null, null));
    }

    /**
     * 根据serviceId查询商店
     *
     * @param serviceId 服务id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #serviceId + #option.toString()")
    public PageInfo<Store> findByServiceId(long serviceId, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")));
        return PageInfo.of(storeMapper.select(String.valueOf(serviceId), "serviceId"));
    }

    /**
     * 根据categoryId查询商店
     *
     * @param categoryId 类别id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #categoryId")
    public List<Store> findByCategoryId(long categoryId) {
        return storeMapper.select(String.valueOf(categoryId), "categoryId");
    }

    /**
     * 根据id查询商店
     *
     * @param id 商店id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #id")
    public List<Store> findById(long id) {
        return storeMapper.select(String.valueOf(id), "id");
    }

    /**
     * 排行榜
     *
     * @return 商店列表
     */
    @Override
    public List<Store> rank() {
        Set<String> range = redisUtil.range("store_rank", 0, 9);
        // 如果排行榜为空，将所有商店加入进去，分数为0
        if (range.size() == 0) {
            List<Store> stores = findAll(new HashMap<>()).getList();
            for (Store store : stores) {
                redisUtil.incrScore("store_rank", String.valueOf(store.getId()), 0);
            }
            range = redisUtil.range("store_rank", 0, 9);
        }
        List<Store> stores = new ArrayList<>();
        for (String id : range) {
            stores.add(findById(Long.parseLong(id)).get(0));
        }
        return stores;
    }

    /**
     * 搜索
     *
     * @param keyword 关键词
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #keyword")
    public List<Store> search(String keyword) {
        return storeMapper.select(keyword, "search");
    }
}
