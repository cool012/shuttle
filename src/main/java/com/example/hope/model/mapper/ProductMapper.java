package com.example.hope.model.mapper;

import com.example.hope.model.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductMapper {

    @Insert("insert into product(product_name,price,image,service_type) values(#{product_name},#{price},#{image},#{service_type})")
    int insert(Product product);

    @Delete("delete from product where id = #{id}")
    int delete(long id);

    @Update("update product set product_name = #{product_name},price = #{price},image = #{image},service_type = #{service_type} where id = #{id}")
    int update(Product product);

    //查询所有产品，按服务id查询
    @Select("select id,product_name,price,image from product where service_type = #{serviceId}")
    List<Product> findAllByType(long serviceId);

    //查询所有产品
    @Select("select id,product_name,price,image,service_type from product")
    List<Product> findAll();
}
