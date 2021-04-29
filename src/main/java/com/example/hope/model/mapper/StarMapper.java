package com.example.hope.model.mapper;

import com.example.hope.common.provider.StarProvider;
import com.example.hope.model.entity.Star;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StarMapper {

    @Insert("insert into star(sid,pid,uid,type) values(#{sid},#{pid},#{uid},#{type})")
    int insert(Star star);

    @Delete("delete from star where id = #{id}")
    int delete(long id);

    @SelectProvider(type = StarProvider.class, method = "findByStore")
    @Results(value = {
            @Result(column = "storeId", property = "store.id"),
            @Result(column = "storeName", property = "store.name"),
            @Result(column = "storeServiceId", property = "store.serviceId"),
            @Result(column = "storeCategoryId", property = "store.categoryId"),
            @Result(column = "storeImage", property = "store.image"),
            @Result(column = "storeRate", property = "store.rate"),
            @Result(column = "storeSales", property = "store.sales")
    })
    List<Star> findByStore(@Param("userId") long userId);

    @SelectProvider(type = StarProvider.class, method = "findByProduct")
    @Results(value = {
            @Result(column = "productId", property = "product.id"),
            @Result(column = "productName", property = "product.name"),
            @Result(column = "productPrice", property = "product.price"),
            @Result(column = "productImage", property = "product.image"),
            @Result(column = "productRate", property = "product.rate"),
            @Result(column = "productSales", property = "product.sales"),
            @Result(column = "productStoreId", property = "product.storeId"),
            @Result(column = "storeName", property = "product.store.name"),
            @Result(column = "storeServiceId", property = "product.store.serviceId"),
            @Result(column = "storeId", property = "product.store.id")
    })
    List<Star> findByProduct(@Param("userId") long userId);

    @Select("select * from star where uid = #{userId} and sid = #{storeId} limit 1")
    List<Star> isStarByStoreId(long userId, long storeId);

    @Select("select * from star where uid = #{userId} and pid = #{productId} limit 1")
    List<Star> isStarByProductId(long userId, long productId);
}
