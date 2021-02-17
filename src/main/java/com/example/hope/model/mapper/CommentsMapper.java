package com.example.hope.model.mapper;

import com.example.hope.model.entity.Comments;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentsMapper {

    @Insert("insert into comments(content,name,storeId,date,userId) values(#{content},#{name},#{storeId},#{date},#{userId})")
    int insert(Comments comments);

    @Delete("delete from comments where id = #{id} and userId = #{userId}")
    int delete(Comments comments);

    @Update("update comments set content = #{content}, storeId = #{storeId}, date = #{date} where id = #{id} and userId = #{userId}")
    int update(Comments comments);

    @Select("select * from comments where storeId = #{storeId}")
    List<Comments> findByStoreId(long storeId);

    @Select("select * from comments")
    List<Comments> findAll();
}
