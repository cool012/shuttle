package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 审批产品类
 * @author: DHY
 * @created: 2021/05/02 16:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveProduct implements Serializable {

    private long id;
    private long uid;
    private boolean status;
    private String name;
    private String image;
    private int price;
    private String storeName;
}