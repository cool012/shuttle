package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @description: 商店实体类
 * @author: DHY
 * @created: 2021/02/03 18:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "store")
public class Store implements Serializable {

    @Id
    private long id;

    @Field(type = FieldType.Text)
    private String name;
    private long serviceId;
    private long categoryId;
    private String image;
    private float rate;
    private int sales;
    private Services services;
    private Category category;
}
