package com.example.hope.model.mapper;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.detail.ProductDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProductMapper {

    @Insert("insert into product(product_name,price,image,service_type,quantity,category_id,shop) values(#{product_name},#{price},#{image},#{service_type},#{quantity},#{category_id},#{shop})")
    int insert(Product product);

    @Delete("delete from product where id = #{id}")
    int delete(long id);

    @Update("update product set product_name = #{product_name},price = #{price},image = #{image},service_type = #{service_type},category_id = #{category_id},shop = #{shop} where id = #{id}")
    int update(Product product);

    //查询所有产品，按服务id查询
    @Select("select product.id,product_name,price,image,service_type,category_id,quantity,sales,shop,rate,service.service_name,category.name as category_name from product,service,category where service.id = product.service_type and category.id = product.category_id and product.service_type = #{serviceId}")
    List<ProductDetail> findAllByType(long serviceId);

    //查询所有产品
    @Select("select product.id,product_name,price,image,service_type,category_id,quantity,sales,shop,rate,service.service_name,category.name as category_name from product,service,category where service.id = product.service_type and category.id = product.category_id")
    List<ProductDetail> findAll();

    //查询所有产品，按服务id、分类查询
    @Select("select product.id,product_name,price,image,service_type,category_id,quantity,sales,shop,rate,service.service_name,category.name as category_name from product,service,category where service.id = product.service_type and category.id = product.category_id and product.service_type = #{serviceId} and product.category_id = #{category_id}")
    List<ProductDetail> findAllByTypeAndCategory(long serviceId, long category_id);

    @Select("select id,name from category where service_id = #{serviceId}")
    Map<Long, String> findAllCategory(long serviceId);

    @Update("update product set sales = sales + #{sales} where id = #{id}")
    int addSales(long id, int sales);

    @Update("update product set rate = (rate + #{rate}) / 2 where id = #{id}")
    int review(long id,int rate);
}
