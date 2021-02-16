package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: 产品sql提供类
 * @author: DHY
 * @created: 2021/02/03 21:00
 */
public class ProductProvider {

    private static final String SQL = "select " +
            "product.*,store.name as storeName,store.serviceId as serviceId, round(product.rate,2) as newRate " +
            "from " +
            "product " +
            "left join " +
            "store on product.storeId = store.Id";

    public String selectByKey(Map<String, Object> para) {
        return Provider.selectByKey(para, SQL, "product");
    }
}
