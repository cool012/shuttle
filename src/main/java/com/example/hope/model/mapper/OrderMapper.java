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

    @SelectProvider(type = OrderProvider.class,method = "chooseIdName")
    List<OrderDetail> findAll(String idName);

    @SelectProvider(type = OrderProvider.class,method = "chooseIdName")
    List<OrderDetail> findByPid(long pid,String idName);

    @SelectProvider(type = OrderProvider.class,method = "chooseIdName")
    List<OrderDetail> findByCid(long cid,String idName);

    @SelectProvider(type = OrderProvider.class,method = "chooseIdName")
    List<OrderDetail> findByUid(long uid,String idName);

    @Update("update order set order_status = 1 where id = #{id}")
    int receiveOrder(long id);

    @SelectProvider(type = OrderProvider.class,method = "isReceived")
    List<OrderDetail> isReceived(boolean received);

    @SelectProvider(type = OrderProvider.class,method = "isCompleted")
    List<OrderDetail> isCompleted(boolean completed);

    @SelectProvider(type = OrderProvider.class,method = "isReceived")
    List<OrderDetail> findByType(long id,boolean received);

    class OrderProvider{

        String sql = "select " +
                "c.id,cid,uid,pid," +
                "e.id as sid," +
                "a.username as user," +
                "b.username as waiter," +
                "d.product_name as product," +
                "e.service_name as type," +
                "create_time,c.address,note,file_url,complete,order_status " +
                "from user as a,user as b,orders as c,product as d,service as e " +
                "where c.cid = a.id " +
                "and c.uid = b.id " +
                "and c.pid = d.id " +
                "and d.service_type = e.id";

        String completed_sql = "and complete = 1";

        String notCompleted_sql = "and complete = 0";

        String received_sql = "and order_status = 1";

        String notReceived_sql = "and order_status = 0";

        String pid = " and c.pid = #{pid}";

        String cid = " and c.cid = #{cid}";

        String uid = " and c.uid = #{uid}";

        public String isCompleted(boolean completed){
            return completed ? sql + completed_sql : sql + notCompleted_sql;
        }

        public String isReceived(boolean received){
            return received ? sql + received_sql : sql + notReceived_sql;
        }

        public String chooseIdName(String idName){
             switch (idName){
                 case "pid":
                     return sql + pid;
                 case "cid":
                     return sql + cid;
                 case "uid":
                     return sql + uid;
             }
             return sql;
        }
    }
}
