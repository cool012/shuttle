package com.example.hope.model.mapper;

import com.example.hope.common.provider.ProductSqlProvider;
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

    @Update("update product set name = #{name},price = #{price},image = #{image},rate = #{rate},storeId = #{storeId} where id = #{id}")
    int update(Product product);

    @SelectProvider(type = ProductSqlProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "storeName", property = "store.name")
    })
    List<Product> findAll();

    @SelectProvider(type = ProductSqlProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "storeName", property = "store.name")
    })
    List<Product> findByKey(@Param("id") String storeId, @Param("key") String key);

    @Update("update product set sales = sales + #{quantity} where id = #{id}")
    int addSales(long id, int quantity);

    @Update("update product set rate = (rate + #{rate}) / 2 where id = #{id}")
    int review(long id, int rate);
}
