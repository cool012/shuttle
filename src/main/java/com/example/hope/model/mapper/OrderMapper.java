package com.example.hope.model.mapper;

import com.example.hope.model.entity.Order;
import com.example.hope.model.entity.OrderDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderMapper {

    @Insert("insert into orders(cid,uid,pid,create_time,address,note,file_url,complete,order_status) values(#{cid},#{uid},#{pid},#{create_time},#{address},#{note},#{file_url},#{complete},#{order_status})")
    int insert(Order order);

    @Delete("delete from orders where id = #{id}")
    int delete(long id);

    @Update("update orders set address = #{address},note = #{note},file_url = #{file_url} where id = #{id}")
    int update(Order order);

    @Select("select orders.id,orders.cid,user.username as username,product.name as name,uid,pid,create_time,orders.address,note,file_url,complete,order_status from orders inner join user on orders.cid = user.id or orders.uid = user.id inner join product on orders.pid = product.id")
    List<OrderDetail> findAll();

    @Select("select orders.id,orders.cid,user.username as username,product.name as name,uid,pid,create_time,orders.address,note,file_url,complete,order_status from orders inner join user on orders.cid = user.id or orders.uid = user.id inner join product on orders.pid = product.id where orders.pid = #{pid}")
    List<OrderDetail> findByPid(long pid);

    @Select("select orders.id,orders.cid,user.username as username,product.name as name,uid,pid,create_time,orders.address,note,file_url,complete,order_status from orders inner join user on orders.cid = user.id or orders.uid = user.id inner join product on orders.pid = product.id where orders.cid = #{cid}")
    List<OrderDetail> findByCid(long cid);

    @Select("select orders.id,orders.cid,user.username as username,product.name as name,uid,pid,create_time,orders.address,note,file_url,complete,order_status from orders inner join user on orders.cid = user.id or orders.uid = user.id inner join product on orders.pid = product.id where orders.uid = #{uid}")
    List<OrderDetail> findByUid(long uid);
}
