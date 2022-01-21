package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.model.entity.Star;
import com.example.hope.model.vo.StarVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StarMapper extends BaseMapper<Star> {

    IPage<StarVO> findByStore(IPage<Star> page, @Param("userId") long userId);

    IPage<StarVO> findByProduct(IPage<Star> page, @Param("userId") long userId);
}
