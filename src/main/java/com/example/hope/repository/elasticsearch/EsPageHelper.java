package com.example.hope.repository.elasticsearch;

import com.example.hope.common.utils.Utils;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 分页包装类
 * @author: DHY
 * @created: 2021/03/07 15:08
 */
@Component
public class EsPageHelper<T> {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchHits build(QueryBuilder queryBuilder, Map<String, String> option, Class<T> clazz) {
        Utils.checkOption(option, null);
        int pageNo = Integer.parseInt(option.get("pageNo"));
        int pageSize = Integer.parseInt(option.get("pageSize"));
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        query.withQuery(queryBuilder);
        query.withPageable(PageRequest.of(pageNo - 1, pageSize));
        return elasticsearchRestTemplate.search(query.build(), clazz);
    }
}
