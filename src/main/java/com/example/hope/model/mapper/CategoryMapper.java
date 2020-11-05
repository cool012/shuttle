package com.example.hope.model.mapper;

import com.example.hope.model.entity.Category;
import com.example.hope.model.entity.Service;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface CategoryMapper {

    @Insert("insert into category(name,service_id) values(#{name},#{service_id})")
    int insert(Category category);

    @Delete("delete from category where id = #{id}")
    int delete(long id);

    @Update("update category set name = #{name},service_id = #{service_id} where id = #{id}")
    int update(Category category);

    @Select("select id,name,service_id from category")
    List<Category> findAll();

    @Select("select id,name,service_id from category where service_id = #{serviceId}")
    List<Category> findAllByServiceId(long serviceId);
}
