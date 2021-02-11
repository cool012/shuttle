package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisUtil;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.ProductMapper;
import com.example.hope.service.ProductService;
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

@Log4j2
@Service
public class ProductServiceIpm implements ProductService {

    private ProductMapper productMapper;
    private StoreService storeService;
    private RedisUtil redisUtil;

    @Autowired
    public ProductServiceIpm(ProductMapper productMapper, StoreService storeService, RedisUtil redisUtil) {
        this.productMapper = productMapper;
        this.storeService = storeService;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加产品
     *
     * @param product
     */
    @Override
    @Transient
    @CacheEvict(value = "product", allEntries = true)
    public void insert(Product product) {
        int res = productMapper.insert(product);
        log.info("product insert -> " + product.toString() + " -> res -> " + res);
        BusinessException.check(res, "添加失败");
    }

    /**
     * 删除产品
     *
     * @param id
     */
    @Override
    @Transient
    @CacheEvict(value = "product", allEntries = true)
    public void delete(long id) {
        int res = productMapper.delete(id);
        log.info("product delete id -> " + id + " -> res -> " + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新产品
     *
     * @param product
     */
    @Override
    @Transient
    @CacheEvict(value = "product", allEntries = true)
    public void update(Product product) {
        int res = productMapper.update(product);
        log.info("product update -> " + product.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部产品
     *
     * @return
     */
    @Override
    @Cacheable(value = "product", key = "methodName + #option.toString()")
    public PageInfo<Product> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")), "product.id desc");
        return PageInfo.of(productMapper.select(null, null));
    }

    /**
     * 根据storeId查询产品
     *
     * @param storeId
     * @return
     */
    @Override
    @Cacheable(value = "product", key = "methodName + #storeId")
    public List<Product> findByStoreId(long storeId) {
        return productMapper.select(String.valueOf(storeId), "storeId");
    }

    /**
     * 根据id查询产品
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "product", key = "methodName + #id")
    public Product findById(long id) {
        return productMapper.select(String.valueOf(id), "id").get(0);
    }

    /**
     * 增加产品销量
     *
     * @param id
     * @param sales
     */
    @Transient
    public void addSales(long id, int sales) {
        int res = productMapper.addSales(id, sales);
        log.info("product addSales -> " + id + " for -> " + sales + " -> res " + res);
        BusinessException.check(res, "更新销量失败");
        redisUtil.incrScore("product_rank", String.valueOf(id), Double.valueOf(sales));
        // 增加商店销量
        storeService.sales(findById(id).getStoreId(), sales);

    }

    /**
     * 更新产品评分
     *
     * @param id
     * @param rate
     */
    @Override
    public void review(long id, int rate) {
        int res = productMapper.review(id, rate);
        log.info("product review -> " + id + " for ->" + rate + " -> res " + res);
        BusinessException.check(res, "更新评分失败");
    }

    /**
     * 排行榜
     *
     * @return
     */
    @Override
    public List<Product> rank() {
        Set<String> rank = redisUtil.range("product_rank", 0, 9);
        // 如果排行榜为空，将所有产品加入进去，分数为0
        if (rank.size() == 0) {
            List<Product> stores = findAll(new HashMap<>()).getList();
            for (Product product : stores) {
                redisUtil.incrScore("product_rank", String.valueOf(product.getId()), 0);
            }
            rank = redisUtil.range("product_rank", 0, 9);
        }
        List<Product> products = new ArrayList<>();
        for (String id : rank) {
            products.add(findById(Long.valueOf(id)));
        }
        return products;
    }

    /**
     * 搜索
     *
     * @return
     */
    @Override
    @Cacheable(value = "product", key = "methodName + #keyword")
    public List<Product> search(String keyword) {
        return productMapper.select(keyword, "search");
    }
}