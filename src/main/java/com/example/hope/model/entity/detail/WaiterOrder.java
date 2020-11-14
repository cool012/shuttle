package com.example.hope.model.entity.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 服务者订单
 * @author: DHY
 * @created: 2020/11/14 10:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaiterOrder implements Serializable {

    private long id;
    private long sid;
    private String product;
    private String category;
}
