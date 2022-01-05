package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.StoreMapper;
import com.example.hope.repository.elasticsearch.EsPageHelper;
import com.example.hope.repository.elasticsearch.StoreRepository;
import com.example.hope.service.*;
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
public class StoreServiceImp extends BaseServiceImp<Store, StoreMapper> implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private ProductService productService;

    @Resource
    private StoreRepository storeRepository;

    @Resource
    private CategoryService categoryService;

    @Resource
    private BusinessService businessService;

    @Resource
    private EsPageHelper<Store> esPageHelper;

    @Resource
    private OrderService orderService;

    /**
     * 添加商店
     *
     * @param store 商店
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public boolean insert(Store store) {
        if (!categoryService.exist(store.getCategoryId()) || !businessService.exist(store.getBusinessId()))
            throw new BusinessException(0, "类别或业务id不存在");
        storeRepository.save(store);
        return this.save(store);
    }

    /**
     * 增加商店销量
     *
     * @param id       商店id
     * @param quantity 数量
     */
    @Override
    public boolean sales(long id, int quantity) {
        Store store = this.getById(id, "商店不存在");
        redisService.review(store.getRate(), store.getSales(), "store_rank", String.valueOf(id));
        store.setSales(store.getSales() + quantity);
        return this.updateById(store);
    }

    /**
     * 删除商店
     *
     * @param id 商店id
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public boolean delete(long id) {
        productService.deleteByStoreId(id);
        storeRepository.deleteById(id);
        return this.removeById(id);
    }

    /**
     * 根据类别id删除商店
     *
     * @param categoryId 类别id
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public boolean deleteByCategoryId(long categoryId) {
        // todo 删除返回布尔
        for (Store store : findByCategoryId(categoryId)) {
            productService.deleteByStoreId(store.getId());
        }
        Wrapper<Store> wrapper = new LambdaQueryWrapper<Store>()
                .eq(Store::getCategoryId, categoryId);
        return this.remove(wrapper);
    }

    /**
     * 更新商店评分
     *
     * @param id    商店id
     * @param rate  评分
     * @param token Token
     */
    @Override
    @CacheEvict(value = "store", allEntries = true)
    public boolean review(long id, float rate, String token) {
        long userId = JwtUtils.getUserId(token);
        List<Orders> list = orderService.findByCid(userId);
        boolean status = false;
        for (Orders orders : list) {
            if (orders.getStoreId() == id) {
                status = true;
                break;
            }
        }
        if (!JwtUtils.is_admin(token) || !status) throw new BusinessException(1, "只允许管理员或在此商店消费过的用户可以评分");
        Store store = this.getById(id, "商店不存在");
        store.setRate(Utils.composeScore(rate, store.getSales()));
        redisService.review(store.getRate(), store.getSales(), "store_rank", String.valueOf(id));
        return this.updateById(store);
    }

    /**
     * 更新商店
     *
     * @param store 商店
     */
    @Override
    @Transactional
    @CacheEvict(value = "store", allEntries = true)
    public boolean update(Store store) {
        boolean res = !categoryService.exist(store.getCategoryId()) || !businessService.exist(store.getBusinessId());
        BusinessException.check(res, "类别或业务id不存在");
        storeRepository.save(store);
        return this.updateById(store);
    }

    /**
     * 查询全部商店
     *
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #option.toString()")
    public PageInfo<Store> page(Map<String, String> option) {
        Utils.checkOption(option, Store.class);
        String orderBy = String.format("store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(this.getList());
    }

    /**
     * 查询全部商店
     *
     * @return List<Store>
     */
    @Cacheable(value = "store", key = "methodName")
    public List<Store> getList() {
        return this.list();
    }

    /**
     * 根据businessId查询商店
     *
     * @param businessId 业务id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #businessId + #option.toString()")
    public PageInfo<Store> findByServiceId(long businessId, Map<String, String> option) {
        Utils.checkOption(option, Store.class);
        String orderBy = String.format("store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        Wrapper<Store> wrapper = new LambdaQueryWrapper<Store>()
                .eq(Store::getBusiness, businessId);
        return PageInfo.of(this.list(wrapper));
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
        return this.list(this.getQueryWrapper(Store::getCategoryId, categoryId));
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
        return PageInfo.of(this.findByCategoryId(categoryId));
    }

    /**
     * 根据id查询商店
     *
     * @param id 商店id
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #id")
    public Store detail(long id) {
        return this.getById(id);
    }

    /**
     * 排行榜
     *
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #option.toString()")
    public List<Store> rank(Map<String, String> option) {
        Utils.checkQuantity(option);
        int quantity = Integer.parseInt(option.get("quantity"));
        Set<String> range = redisService.range("store_rank", 0, (quantity - 1));
        // 如果排行榜为空，将所有商店加入进去，分数为0
        if (range.size() == 0) {
            List<Store> stores = getList();
            for (Store store : stores) {
                double score = Utils.changeRate(store.getRate(), store.getSales());
                redisService.incrScore("store_rank", String.valueOf(store.getId()), score);
            }
            range = redisService.range("store_rank", 0, (quantity - 1));
        }
        List<Store> stores = new ArrayList<>();
        for (String id : range) {
            stores.add(detail(Long.parseLong(id)));
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
     * @return boolean true存在，false不存在
     */
    @Override
    public boolean exist(long id) {
        return detail(id) != null;
    }

    /**
     * 根据名字查询商店
     *
     * @param name 名字
     * @return 商店列表
     */
    @Override
    @Cacheable(value = "store", key = "methodName + #name")
    public List<Store> findByName(String name) {
        return storeMapper.findByName(name);
    }
}
