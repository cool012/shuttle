package com.example.hope.model.mapper;

import com.example.hope.model.entity.Service;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ServiceMapper {

    @Insert("insert into service(service_name) values(#{serviceName})")
    int insert(String serviceName);

    @Delete("delete from service where id = #{id}")
    int delete(long id);

    @Select("select id,service_name from service")
    List<Service> findAll();
}
