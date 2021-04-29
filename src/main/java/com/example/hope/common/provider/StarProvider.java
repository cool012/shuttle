package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: 收藏sql提供类
 * @author: DHY
 * @created: 2021/02/03 21:00
 */
public class StarProvider {

    private static final String store = "select " +
            "star.*," +
            "store.id as storeId," +
            "store.name as storeName," +
            "store.serviceId as storeServiceId," +
            "store.categoryId as storeCategoryId," +
            "store.image as storeImage," +
            "store.rate as storeRate," +
            "store.sales as storeSales " +
            "from star " +
            "left join store " +
            "on " +
            "star.sid = store.id " +
            "where " +
            "uid = %s" +
            "and " +
            "type = 0;";

    private static final String product = "select " +
            "star.*," +
            "product.id as productId," +
            "product.name as productName," +
            "product.price as productPrice," +
            "product.image as productImage," +
            "product.rate as productRate," +
            "product.sales as productSales," +
            "product.storeId as productStoreId," +
            "store.name as storeName," +
            "store.serviceId as StoreServiceId," +
            "store.id as storeId " +
            "from star " +
            "left join product " +
            "on " +
            "star.pid = product.id " +
            "left join store on product.storeId = store.id " +
            "where " +
            "uid = %s" +
            "and " +
            "type = 1;";

    public String findByStore(Map<String, Object> para) {
        return String.format(store, para.get("userId"));
    }

    public String findByProduct(Map<String, Object> para) {
        return String.format(product, para.get("userId"));
    }
}
