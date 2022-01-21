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
            "business.name as businessName," +
            "business.color as businessColor," +
            "category.name as categoryName " +
            "from store " +
            "left join " +
            "business " +
            "on " +
            "store.business_id = business.id " +
            "left join " +
            "category " +
            "on " +
            "store.category_id = category.id";

    public String selectByKey(Map<String, Object> para) {
        return Provider.selectByKey(para, SQL, "store");
    }
}
