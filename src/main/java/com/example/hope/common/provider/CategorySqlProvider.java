package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: 类别表sql提供类
 * @author: DHY
 * @created: 2021/02/06 20:00
 */
public class CategorySqlProvider {

    private static final String SQL = "select " +
            "category.*," +
            "service.name as serviceName," +
            "service.color as serviceColor," +
            "service.icon as serviceIcon " +
            "from " +
            "category " +
            "left join " +
            "service " +
            "on " +
            "category.serviceId = service.id";

    public String selectByKey(Map<String, Object> para) {
        return Provider.selectByKey(para, SQL,"category");
    }
}
