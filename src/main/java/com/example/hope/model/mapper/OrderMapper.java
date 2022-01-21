package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.vo.OrdersVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

    IPage<OrdersVO> selectByPage(IPage<OrdersVO> page, @Param(Constants.WRAPPER) Wrapper<Orders> wrapper);

    List<OrdersVO> selectByList(@Param(Constants.WRAPPER) Wrapper<Orders> wrapper);

    OrdersVO detail(@Param("id") Long id);
}