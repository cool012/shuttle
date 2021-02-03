package com.example.hope.model.mapper;

import com.example.hope.model.entity.Service;
import com.example.hope.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ServiceMapper {

    @Insert("insert into service(name,color,icon) values(#{name},#{color},#{icon})")
    int insert(Service service);

    @Delete("delete from service where id = #{id}")
    int delete(long id);

    @Update("update service set name = #{name},color = #{color},icon = #{icon} where id = #{id}")
    int update(Service service);

    @Select("select id,name,color,icon from service")
    List<Service> findAll();
}
