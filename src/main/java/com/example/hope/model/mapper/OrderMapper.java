package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hope.common.provider.OrdersProvider;
import com.example.hope.model.entity.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

    // 批量添加订单
    int insertBatch(List<Orders> orderList);

    // 批量删除订单
    int deleteBatch(List<Orders> orders);

    @Insert("insert into orders(cid,sid,pid,date,address,note,file,status) values(#{cid},#{sid},#{pid},#{date},#{address},#{note},#{file},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Orders order);

    @Delete("delete from orders where ${key} = #{id}")
    int delete(long id, String key);

    @Update("update orders set address = #{address},date = #{date},note = #{note},file = #{file},status = #{status} where id = #{id}")
    int update(Orders order);

    @SelectProvider(type = OrdersProvider.class, method = "selectByKey")
    @Results(value = {
            @Result(column = "clientName", property = "client.name"),
            @Result(column = "clientPhone", property = "client.phone"),
            @Result(column = "clientAddress", property = "client.address"),
            @Result(column = "serviceName", property = "service.name"),
            @Result(column = "servicePhone", property = "service.phone"),
            @Result(column = "productId", property = "product.id"),
            @Result(column = "productName", property = "product.name"),
            @Result(column = "productImage", property = "product.image"),
            @Result(column = "productPrice", property = "product.price"),
            @Result(column = "productRate", property = "product.rate"),
            @Result(column = "productSales", property = "product.sales")
    })
    List<Orders> select(@Param("id") String id, @Param("key") String key, @Param("status") String status);

    @Update("update orders set status = 0,sid = #{sid} where id = #{id}")
    int receive(long id, long sid);

    @Update("update orders set status = 1 where id = #{id}")
    int completed(long id);

    List<Orders> searchByCid(long userId, String start, String end, long productId, long serverId, int status);


    List<Orders> searchByReceive(@Param("start") String start, @Param("end") String end,
                                 @Param("serviceId") long serviceId, @Param("address") String address);
}