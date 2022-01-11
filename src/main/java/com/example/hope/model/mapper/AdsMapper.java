package com.example.hope.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hope.model.entity.Ads;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AdsMapper extends BaseMapper<Ads> {

    @Insert("insert into ads(image,storeId) values(#{image},#{storeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Ads ads);

    @Delete("delete from ads where ${key} = #{id}")
    int delete(long id, String key);

    @Update("update ads set image = #{image},storeId = #{storeId} where id = #{id}")
    int update(Ads ads);

    @Select("select * from ads")
    List<Ads> select();
}
