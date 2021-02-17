package com.example.hope.model.mapper;

import com.example.hope.common.provider.ProductProvider;
import com.example.hope.model.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductMapper {

    @Insert("insert into product(name,price,image,quantity,rate,storeId) values(#{name},#{price},#{image},#{quantity},#{rate},#{storeId})")
    int insert(Product product);

    @Delete("delete from product where id = #{id}")
    int delete(long id);

    @Update("update product set name = #{name},price = #{price},image = #{image},rate = #{rate},storeId = #{storeId}, sales = #{sales} where id = #{id}")
    int update(Product product);

    @SelectProvider(type = ProductProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "newRate", property = "rate"),
            @Result(column = "storeName", property = "store.name"),
            @Result(column = "serviceId", property = "store.serviceId")

    })
    List<Product> select(@Param("id") String storeId, @Param("key") String key);

    @Update("update product set sales = sales + #{quantity} where id = #{id}")
    int addSales(long id, int quantity);

    @Update("update product set rate = (rate * sales + #{rate}) / (sales + 1) where id = #{id}")
    int review(long id, float rate);
}
