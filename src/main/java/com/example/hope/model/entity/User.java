package com.example.hope.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @description: 用户实体类
 * @author: DHY
 * @created: 2020/10/23 19:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @Id
    private Long id;

    private String password;

    @Field(type = FieldType.Text)
    private String phone;

    private String address;

    private int score;

    private boolean admin;

    @Field(type = FieldType.Text)
    private String name;

    private String email;


}