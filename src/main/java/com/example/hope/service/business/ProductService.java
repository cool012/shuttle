package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Product;
import com.example.hope.model.vo.ProductVO;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product> {

    boolean insert(Product product);

    boolean delete(long id);

    boolean deleteByStoreId(long storeId);

    boolean update(Product product);

    boolean review(Product product, String token, long orderId);

    boolean addSales(long id, int sales);

    boolean exist(long id);

    List<ProductVO> rank(Map<String, String> option);

    List<Product> findByStoreId(long storeId);

    SearchHits<Product> search(String keyword, Map<String, String> option);

    IPage<ProductVO> page(Query query);

    IPage<ProductVO> findByStoreId(long storeId, Query query);

    ProductVO findById(long id);
}
