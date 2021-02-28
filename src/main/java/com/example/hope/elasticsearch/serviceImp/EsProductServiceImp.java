package com.example.hope.elasticsearch.serviceImp;

import com.example.hope.elasticsearch.repository.ProductRepository;
import com.example.hope.model.entity.Product;
import com.example.hope.elasticsearch.service.EsProductService;
import com.example.hope.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 产品搜索实现类
 * @author: DHY
 * @created: 2021/02/28 13:54
 */
@Service
public class EsProductServiceImp implements EsProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductService productService;

    /**
     * 导出数据库全部数据
     */
    @Override
    public void importAll() {
        List<Product> products = productService.findAll(new HashMap<>()).getList();
        productRepository.saveAll(products);
    }

    /**
     * 保存产品到es
     *
     * @param product 产品
     */
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * 从es删除产品
     *
     * @param productId 产品id
     */
    @Override
    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    /**
     * 根据产品名搜索
     *
     * @param keyword 关键词
     * @return 产品列表
     */
    @Override
    public List<Product> search(String keyword) {
        return productRepository.queryProductByName(keyword);
    }
}
