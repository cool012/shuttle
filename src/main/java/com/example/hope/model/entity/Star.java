package com.example.hope.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 收藏实体类
 * @author: DHY
 * @created: 2021/04/27 11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Star implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private long id;

    private long sid;

    private long pid;

    private long uid;

    private boolean type;

    private Store store;

    private Product product;
}