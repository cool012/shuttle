package com.example.hope.repository.elasticsearch;

import com.example.hope.model.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

    List<Product> queryProductByName(String name);
}
