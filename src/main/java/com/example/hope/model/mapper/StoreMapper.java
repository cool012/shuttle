package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.hope.model.entity.Store;
import com.example.hope.model.vo.StoreVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    List<StoreVO> findAll();

    StoreVO findByName(@Param("name") String name);

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @return 分页结果
     */
    IPage<StoreVO> selectByPage(IPage<StoreVO> page, @Param(Constants.WRAPPER) Wrapper<Store> wrapper);

    /**
     * 详情
     * @param id 商店 id
     * @return StoreVO
     */
    StoreVO detail(@Param("id") Long id);
}
