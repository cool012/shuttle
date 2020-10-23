package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private long id;
    private String product_name;
    private int price;
    private String image;
    private int service_type;

    public Product(String product_name, int price, String image, int service_type) {
        this.product_name = product_name;
        this.price = price;
        this.image = image;
        this.service_type = service_type;
    }
}
