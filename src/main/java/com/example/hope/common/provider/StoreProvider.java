package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: 商店sql提供类
 * @author: DHY
 * @created: 2021/02/03 21:00
 */
public class StoreProvider {

    private static final String SQL = "select " +
            "store.*," +
            "service.name as serviceName," +
            "service.color as serviceColor," +
            "category.name as categoryName " +
            "from store " +
            "left join " +
            "service " +
            "on " +
            "store.serviceId = service.id " +
            "left join " +
            "category " +
            "on " +
            "store.categoryId = category.id";

    public String selectByKey(Map<String, Object> para) {
        return Provider.selectByKey(para, SQL, "store");
    }
}