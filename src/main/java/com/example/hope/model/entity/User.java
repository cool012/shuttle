package com.example.hope.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 用户实体类
 * @author: DHY
 * @created: 2020/10/23 19:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private long id;
    private String password;
    private String phone;
    private String address;
    private int score;
    private boolean admin;
    private String name;

    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(String password, String phone, String address, int score, boolean admin, String name) {
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.score = score;
        this.admin = admin;
        this.name = name;
    }
}