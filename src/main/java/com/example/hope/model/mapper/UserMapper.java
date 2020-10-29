package com.example.hope.model.mapper;

import com.example.hope.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user(password,email,address,type,score) values(#{password},#{email},#{address},#{type},#{score})")
    int insert(User user);

    @Delete("delete from user where id = #{id}")
    int delete(long id);

    @Update("update user set address = #{address} where id = #{id}")
    int update(User user);

    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(User user);

    @Update("update user set score = score + #{quantity} where id = #{id}")
    int addScore(long id,int quantity);

    @Update("update user set score = score - 1 where id = #{id}")
    int reduceScore(long id);

    @Select("select id,email,address,type,score from user where id = #{id}")
    User findUserById(long id);

    @Select("select id,email,address,type,score from user")
    List<User> findAll();

    @Select("select id,type from user where email = #{email} and password = #{encryption_password}")
    User login(String email,String encryption_password);

    @Select("select id,address,type,score from user where email = #{email}")
    User findByEmail(String email);

    @Select("select score from user where id = #{id}")
    int findByScore(long id);
}
