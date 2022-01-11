package com.example.hope.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Business implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String color;

    private String icon;
}