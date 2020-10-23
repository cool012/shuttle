package com.example.hope.service;

import com.example.hope.config.BusinessException;
import com.example.hope.model.entity.Product;
import com.example.hope.model.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ProductService {

    private ProductMapper productMapper;

    @Autowired
    public ProductService(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    /**
     * 添加产品
     * @param product
     */
    public void insert(Product product){
        int res = productMapper.insert(product);
        log.info("product insert -> " + product.toString() + " -> res -> " + res);
        BusinessException.isExist(res,"添加失败");
    }

    /**
     * 删除产品
     * @param id
     */
    public void delete(long id){
        int res = productMapper.delete(id);
        log.info("product delete id -> " + id + " -> res -> " + res);
        BusinessException.isExist(res,"删除失败");
    }

    /**
     * 更新产品
     * @param product
     */
    public void update(Product product){
        int res = productMapper.update(product);
        log.info("product update -> " + product.toString() + " -> res -> " + res);
        BusinessException.isExist(res,"更新失败");
    }

    /**
     * 根据类型查询产品
     * @param serviceId
     * @return
     */
    public List<Product> findAllByType(long serviceId){
        List<Product> productList = productMapper.findAllByType(serviceId);
        log.info("findAllByType find serviceId -> " + productList.toString());
        return productList;
    }

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        List<Product> productList = productMapper.findAll();
        log.info("findAll -> " + productList.toString());
        return productList;
    }
}
