package com.example.hope.model.mapper;

import com.example.hope.model.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProductMapper {

    @Insert("insert into product(product_name,price,image,service_type,quantity,category_id) values(#{product_name},#{price},#{image},#{service_type},#{quantity},#{category_id})")
    int insert(Product product);

    @Delete("delete from product where id = #{id}")
    int delete(long id);

    @Update("update product set product_name = #{product_name},price = #{price},image = #{image},service_type = #{service_type},category_id = #{category_id} where id = #{id}")
    int update(Product product);

    //查询所有产品，按服务id查询
    @Select("select id,product_name,price,image,service_type,category_id,sales from product where service_type = #{serviceId}")
    List<Product> findAllByType(long serviceId);

    //查询所有产品
    @Select("select id,product_name,price,image,service_type,category_id,sales from product")
    List<Product> findAll();

    //查询所有产品，按服务id、分类查询
    @Select("select id,product_name,price,image,service_type,category_id,sales from product where service_type = #{serviceId} and category_id = #{category_id}")
    List<Product> findAllByTypeAndCategory(long serviceId,long category_id);

    @Select("select id,name from category where service_id = #{serviceId}")
    Map<Long,String> findAllCategory(long serviceId);

    @Update("update product set sales = sales + #{sales} where id = #{id}")
    int addSales(long id,int sales);
}
