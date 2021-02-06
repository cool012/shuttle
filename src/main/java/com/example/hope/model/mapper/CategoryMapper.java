package com.example.hope.model.mapper;

import com.example.hope.common.provider.CategorySqlProvider;
import com.example.hope.model.entity.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CategoryMapper {

    @Insert("insert into category(name,serviceId) values(#{name},#{serviceId})")
    int insert(Category category);

    @Delete("delete from category where id = #{id}")
    int delete(long id);

    @Update("update category set name = #{name},serviceId = #{serviceId} where id = #{id}")
    int update(Category category);

    @SelectProvider(type = CategorySqlProvider.class, method = "selectByKey")
    @Results({
            @Result(column = "serviceName", property = "service.name"),
            @Result(column = "serviceColor", property = "service.color"),
            @Result(column = "serviceIcon", property = "service.icon")
    })
    List<Category> select(@Param("id") String id, @Param("key") String key);
}
