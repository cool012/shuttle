package com.example.hope.model.mapper;

import com.example.hope.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user(username,encryption_password,email,address,user_type,is_admin,score) values(#{username},#{encryption_password},#{email},#{address},#{user_type},#{is_admin},#{score})")
    int insert(User user);

    @Delete("delete from user where id = #{id}")
    int delete(long id);

    @Update("update user set encryption_password = #{encryption_password},email = #{email},address = #{address} where id = #{id}")
    int update(User user);

    @Select("select id,username,encryption_password,email,address,user_type,is_admin,score from user where id = #{id}")
    List<User> findUserById(long id);

    @Select("select id,username,encryption_password,email,address,user_type,is_admin,score from user")
    List<User> findAll();

    @Select("select count(*) from user where username = #{username} and encryption_password = #{encryption_password}")
    int login(String username,String encryption_password);
}
