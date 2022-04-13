package com.example.hope.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 分页参数类
 * @Author duhongyu
 * @Data 2022/1/21 11:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Query {

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页的页数
     */
    private Integer size;
}
