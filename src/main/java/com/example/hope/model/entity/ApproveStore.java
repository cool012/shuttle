package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 审批商店类
 * @author: DHY
 * @created: 2021/05/02 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveStore implements Serializable {

    private long id;
    private long uid;
    private boolean status;
    private String name;
    private String image;
    private long serviceId;
    private long categoryId;
    private String categoryName;
}