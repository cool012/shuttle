package com.example.hope.service.serviceIpm;

import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.StoreMapper;
import com.example.hope.repository.elasticsearch.EsPageHelper;
import com.example.hope.repository.elasticsearch.StoreRepository;
import com.example.hope.service.CategoryService;
import com.example.hope.service.ServiceService;
import com.example.hope.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description: 商店服务实现类
 * @author: DHY
 * @created: 2021/02/03 19:48
 */

@Service
@Log4j2
public class StoreServiceImp implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private ProductServiceIpm productServiceIpm;

    @Resource
    private StoreRepository storeRepository;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ServiceService serviceService;

    @Resource
    private EsPageHelper<Store> esPageHelper;

    /**
     * 添加商店
     *
     * @param store 商店
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public void insert(Store store, String token) {
        boolean isAdmin = JwtUtils.is_admin(token);
        if (!categoryService.exist(store.getCategoryId()) || !serviceService.exist(store.getServiceId()))
            throw new BusinessException(0, "类别或服务id不存在");
        if (isAdmin) {
            int res = storeMapper.insert(store);
            log.info(LoggerHelper.logger(store, res));
            BusinessException.check(res, "添加失败");
            storeRepository.save(store);
        } else {
            // todo 管理端同意
        }
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
        redisService.incrScore("store_rank", String.valueOf(id), quantity);
    }

    /**
     * 删除商店
     *
     * @param id 商店id
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public void delete(long id) {
        int res = storeMapper.delete(id, "id");
        productServiceIpm.deleteByStoreId(id);
        log.info(LoggerHelper.logger(id, res));
        BusinessException.check(res, "删除失败");
        storeRepository.deleteById(id);
    }

    /**
     * 根据类别id删除商店
     *
     * @param categoryId 类别id
     */
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public void deleteByCategoryId(long categoryId) {
        for (Store store : findByCategoryId(categoryId)) productServiceIpm.deleteByStoreId(store.getId());
        int res = storeMapper.delete(categoryId, "categoryId");
        log.info(LoggerHelper.logger(categoryId, res));
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
        log.info(LoggerHelper.logger(id, res));
        BusinessException.check(res, "更新失败");
    }

    /**
     * 更新商店
     *
     * @param store 商店
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public void update(Store store) {
        if (!categoryService.exist(store.getCategoryId()) || !serviceService.exist(store.getServiceId()))
            throw new BusinessException(0, "类别或服务id不存在");
        int res = storeMapper.update(store);
        log.info(LoggerHelper.logger(store, res));
        BusinessException.check(res, "更新失败");
        storeRepository.save(store);
    }

    /**
     * 查询全部商店
     *
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #option.toString()")
    public PageInfo<Store> findAll(Map<String, String> option) {
        Utils.checkOption(option, Store.class);
        String orderBy = String.format("store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
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
        Utils.checkOption(option, Store.class);
        String orderBy = String.format("store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
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
     * 根据categoryId查询商店（分页）
     *
     * @param categoryId 类别id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #categoryId + #option.toString()")
    public PageInfo<Store> findByCategoryId(long categoryId, Map<String, String> option) {
        Utils.checkOption(option, Store.class);
        String orderBy = String.format("store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(storeMapper.select(String.valueOf(categoryId), "categoryId"));
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
        Set<String> range = redisService.range("store_rank", 0, 9);
        // 如果排行榜为空，将所有商店加入进去，分数为0
        if (range.size() == 0) {
            List<Store> stores = findAll(new HashMap<>()).getList();
            for (Store store : stores) {
                redisService.incrScore("store_rank", String.valueOf(store.getId()), 0);
            }
            range = redisService.range("store_rank", 0, 9);
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
    public SearchHits search(String keyword, Map<String, String> option) {
        return esPageHelper.build(QueryBuilders.matchQuery("name", keyword), option, Store.class);
    }

    /**
     * 是否存在商店
     *
     * @param id 商店id
     * @return boolean
     */
    @Override
    public boolean exist(long id) {
        return findById(id).size() != 0;
    }
}
