package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.hope.model.entity.Product;
import com.example.hope.model.vo.ProductVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    IPage<ProductVO> selectByPage(IPage<ProductVO> page, @Param(Constants.WRAPPER) Wrapper<Product> wrapper);

    ProductVO detail(@Param("id") Long id);
}
