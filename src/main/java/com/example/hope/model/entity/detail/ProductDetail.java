package com.example.hope.model.entity.detail;

import com.example.hope.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 产品详细类
 * @author: DHY
 * @created: 2020/11/07 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail extends Product implements Serializable {

    private long pid;
    private String service_name;
    private String category_name;
}
