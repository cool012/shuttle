package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: 产品sql提供类
 * @author: DHY
 * @created: 2021/02/03 21:00
 */
public class ProductSqlProvider {

    private static final String sql = "select " +
            "product.*,store.name as storeName " +
            "from " +
            "product " +
            "left join " +
            "store on product.storeId = store.Id";

    public String selectByKey(Map<String, Object> para) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sql);
        if (para != null) {
            if (para.get("key").equals("search"))
                stringBuffer.append(" where product.name like %" + para.get("keyword") + "%");
            else stringBuffer.append(" where product." + para.get("key") + " = " + para.get("id"));
        }
        stringBuffer.append(";");
        return stringBuffer.toString();
    }
}
