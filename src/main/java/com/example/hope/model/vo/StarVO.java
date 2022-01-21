package com.example.hope.model.vo;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.Star;
import com.example.hope.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 收藏视图实体类
 * @Author duhongyu
 * @Data 2022/1/21 16:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarVO extends Star {

    private Store store;

    private Product product;
}
