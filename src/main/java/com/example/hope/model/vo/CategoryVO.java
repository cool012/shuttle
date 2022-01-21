package com.example.hope.model.vo;

import com.example.hope.model.entity.Business;
import com.example.hope.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 类别视图实体类
 * @Author duhongyu
 * @Data 2022/1/21 16:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO extends Category {

    private Business business;
}