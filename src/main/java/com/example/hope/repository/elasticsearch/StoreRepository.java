package com.example.hope.repository.elasticsearch;

import com.example.hope.model.entity.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends ElasticsearchRepository<Store, Long> {

    List<Store> queryStoreByName(String name);

    @Override
    Optional<Store> findById(Long aLong);
}
