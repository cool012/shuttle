package com.example.hope.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页实体类
 * @author: DHY
 * @created: 2021/03/03 12:57
 */
@Data
public class Page<T> implements Serializable {

    private List<T> list;
    private int count;
    private int pageNo;
    private int pageSize = 9;
    private int total;
}
