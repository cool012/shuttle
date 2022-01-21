package com.example.hope.model.vo;

import com.example.hope.model.entity.Business;
import com.example.hope.model.entity.Category;
import com.example.hope.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 商店视图类
 * @Author duhongyu
 * @Data 2022/1/20 11:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreVO extends Store {

    private Business business;

    private Category category;
}
