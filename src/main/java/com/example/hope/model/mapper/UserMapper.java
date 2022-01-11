package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hope.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
