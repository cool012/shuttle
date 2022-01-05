package com.example.hope.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product")
public class Product implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private long id;

    @Field(type = FieldType.Text)
    private String name;

    private int price;

    private String image;

    private int sales;

    private float rate;

    private long storeId;

    private Store store;

    private int quantity;

}