package com.example.hope.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hope.model.bo.Query;

/**
 * @Description 分页工具类
 * @Author duhongyu
 * @Data 2022/1/21 11:07
 **/
public class PageUtils {

    public static <T> Page<T> getQuery(Query query) {
        if (query.getCurrent() == null) {
            query.setCurrent(1);
        }
        if (query.getSize() == null) {
            query.setSize(10);
        }
        return new Page<>(query.getCurrent(), query.getSize());
    }
}
