package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 类别实体类
 * @author: DHY
 * @created: 2020/10/30 13:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private long id;
    private String name;
    private long service_id;
}
