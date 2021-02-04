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
    private String name;
    private int price;
    private String image;
    private int quantity;
    private int sales;
    private int rate;
    private long storeId;
    private Store store;
}