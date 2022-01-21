package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.hope.model.entity.Category;
import com.example.hope.model.vo.CategoryVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    IPage<CategoryVO> selectByPage(IPage<Category> page);

    List<CategoryVO> selectByList(@Param(Constants.WRAPPER) Wrapper<Category> wrapper);

    CategoryVO detail(@Param("id") Long id);
}
