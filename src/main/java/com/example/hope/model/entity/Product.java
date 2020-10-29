package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private long id;
    private String product_name;
    private int price;
    private String image;
    private int service_type;
    private int quantity;
    private long category_id;
    private int sales;
}