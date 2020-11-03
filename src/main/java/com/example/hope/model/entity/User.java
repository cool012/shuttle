package com.example.hope.model.entity;

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
    private String email;
    private String address;
    private String type;
    private int score;

    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(String password, String email, String address, String type, int score) {
        this.password = password;
        this.email = email;
        this.address = address;
        this.type = type;
        this.score = score;
    }
}