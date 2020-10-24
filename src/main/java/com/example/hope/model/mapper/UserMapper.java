package com.example.hope.model.mapper;

import com.example.hope.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user(username,password,email,address,type,admin,score) values(#{username},#{password},#{email},#{address},#{type},#{admin},#{score})")
    int insert(User user);

    @Delete("delete from user where id = #{id}")
    int delete(long id);

    @Update("update user set email = #{email},address = #{address} where id = #{id}")
    int update(User user);

    @Select("select id,username,password,email,address,type,admin,score from user where id = #{id}")
    User findUserById(long id);

    @Select("select id,username,password,email,address,type,admin,score from user")
    List<User> findAll();

    @Select("select id,username,admin from user where username = #{username} and password = #{encryption_password}")
    User login(String username,String encryption_password);

    @Select("select id,username,password,email,address,type,admin,score from user where username = #{name}")
    User findByName(String name);
}
