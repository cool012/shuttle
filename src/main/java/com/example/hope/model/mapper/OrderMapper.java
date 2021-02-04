package com.example.hope.model.mapper;

import com.example.hope.common.provider.OrdersSqlProvider;
import com.example.hope.model.entity.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderMapper {

    // 批量添加订单
    int insertBatch(List<Orders> orderList);

    @Insert("insert into orders(cid,sid,pid,date,address,note,file,status) values(#{cid},#{sid},#{pid},#{date},#{address},#{note},#{file},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Orders order);

    @Delete("delete from orders where id = #{id}")
    int delete(long id);

    @Update("update orders set address = #{address},note = #{note},file = #{file} where id = #{id}")
    int update(Orders order);

    @SelectProvider(type = OrdersSqlProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "clientName", property = "client.name"),
            @Result(column = "clientPhone", property = "client.phone"),
            @Result(column = "serviceName", property = "service.name"),
            @Result(column = "servicePhone", property = "service.phone"),
            @Result(column = "productName", property = "product.name"),
            @Result(column = "productPrice", property = "product.price"),
            @Result(column = "productId", property = "product.id"),
    })
    List<Orders> findAll();

    @SelectProvider(type = OrdersSqlProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "clientName", property = "client.name"),
            @Result(column = "clientPhone", property = "client.phone"),
            @Result(column = "serviceName", property = "service.name"),
            @Result(column = "servicePhone", property = "service.phone"),
            @Result(column = "productName", property = "product.name"),
            @Result(column = "productPrice", property = "product.price"),
            @Result(column = "productId", property = "product.id"),
    })
    List<Orders> findByKey(@Param("id") long id, @Param("key") String key);

    @Update("update orders set status = 0,sid = #{sid} where id = #{id}")
    int receive(long id, long sid);

    @Update("update orders set status = 1 where id = #{id}")
    int completed(long id);
}
