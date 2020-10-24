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
    private String username;
    private String password;
    private String email;
    private String address;
    private boolean type;
    private boolean admin;
    private int score;

    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }
}