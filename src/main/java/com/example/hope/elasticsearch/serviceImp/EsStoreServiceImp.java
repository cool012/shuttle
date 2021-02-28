package com.example.hope.elasticsearch.serviceImp;

import com.example.hope.elasticsearch.repository.StoreRepository;
import com.example.hope.elasticsearch.service.EsStoreService;
import com.example.hope.model.entity.Store;
import com.example.hope.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 商店搜索实现类
 * @author: DHY
 * @created: 2021/02/28 13:54
 */
@Service
public class EsStoreServiceImp implements EsStoreService {

    @Resource
    private StoreRepository StoreRepository;

    @Resource
    private StoreService StoreService;

    /**
     * 导出数据库全部数据
     */
    @Override
    public void importAll() {
        List<Store> Stores = StoreService.findAll(new HashMap<>()).getList();
        StoreRepository.saveAll(Stores);
    }

    /**
     * 保存商店到es
     *
     * @param Store 商店
     */
    @Override
    public void save(Store Store) {
        StoreRepository.save(Store);
    }

    /**
     * 从es删除商店
     *
     * @param StoreId 商店id
     */
    @Override
    public void delete(long StoreId) {
        StoreRepository.deleteById(StoreId);
    }

    /**
     * 根据商店名搜索
     *
     * @param keyword 关键词
     * @return 商店列表
     */
    @Override
    public List<Store> search(String keyword) {
        return StoreRepository.queryStoreByName(keyword);
    }
}
