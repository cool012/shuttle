package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.detail.ProductDetail;
import com.example.hope.model.mapper.ProductMapper;
import com.example.hope.service.ProductService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ProductServiceIpm implements ProductService {

    private ProductMapper productMapper;

    @Autowired
    public ProductServiceIpm(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 添加产品
     *
     * @param product
     */
    @Override
    @Transient
    @CacheEvict(value = "product",allEntries = true)
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
    @CacheEvict(value = "product",allEntries = true)
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
    @CacheEvict(value = "product",allEntries = true)
    public void update(Product product) {
        int res = productMapper.update(product);
        log.info("product update -> " + product.toString() + " -> res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 根据类型查询产品
     *
     * @param serviceId
     * @return
     */
    @Override
    @Cacheable(value = "product",key = "methodName + #serviceId")
    public List<ProductDetail> findAllByType(long serviceId,Map<String,String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")),Integer.valueOf(option.get("pageSize")));
        return productMapper.findAllByType(serviceId);
    }

    /**
     * 查询全部产品
     *
     * @return
     */
    @Override
    @Cacheable(value = "product",key = "methodName")
    public List<ProductDetail> findAll(Map<String,String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")),Integer.valueOf(option.get("pageSize")));
        return productMapper.findAll();
    }

    /**
     * 根据类型、分类查询产品
     *
     * @param serviceId
     * @param categoryId
     * @return
     */
    @Override
    @Cacheable(value = "product",key = "methodName + #serviceId + #categoryId")
    public List<ProductDetail> findAllByTypeAndCategory(long serviceId, long categoryId, Map<String,String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")),Integer.valueOf(option.get("pageSize")));
        return productMapper.findAllByTypeAndCategory(serviceId, categoryId);
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
        log.info("product addSales -> " + id + " for -> " + sales + " -> res" + res);
        BusinessException.check(res, "更新销量失败");
    }
}