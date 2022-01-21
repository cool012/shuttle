package com.example.hope.model.vo;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 产品视图类
 * @Author duhongyu
 * @Data 2022/1/21 13:32
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO extends Product {

    private Store store;
}