package com.example.hope.model.mapper;

import com.example.hope.common.provider.StoreSqlProvider;
import com.example.hope.model.entity.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StoreMapper {

    @Insert("insert into store(name,serviceId,categoryId,image,rate,sales) values(#{name},#{serviceId},#{categoryId},#{image},#{rate},#{sales})")
    int insert(Store store);

    @Delete("delete from store where id = #{id}")
    int delete(long id);

    @Update("update store set name = #{name}, serviceId = #{serviceId}, categoryId = #{categoryId}, image = #{image}, rate = #{rate}, sales = #{sales} where id = #{id}")
    int update(Store store);

    @SelectProvider(type = StoreSqlProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "serviceName", property = "service.name"),
            @Result(column = "serviceColor", property = "service.color"),
            @Result(column = "categoryName", property = "category.name")
    })
    List<Store> select(@Param("id") String id, @Param("key") String key);

    @Update("update store set sales = sales + #{quantity} where id = #{id}")
    int sales(long id,int quantity);
}