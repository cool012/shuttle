package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hope.model.entity.Business;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BusinessMapper extends BaseMapper<Business> {

    @Insert("insert into service(name,color,icon) values(#{name},#{color},#{icon})")
    int insert(Business business);

    @Delete("delete from service where id = #{id}")
    int delete(long id);

    @Update("update service set name = #{name},color = #{color},icon = #{icon} where id = #{id}")
    int update(Business business);

    @Select("select id,name,color,icon from service")
    List<Business> findAll();

    @Select("select * from service where id = #{serviceId}")
    List<Business> findById(long serviceId);
}
