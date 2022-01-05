package com.example.hope.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrdersState {

    HAVE(-1, "已下单"),
    SERVICING(0, "派送中"),
    COMPLETE(1, "已完成");

    private Integer code;
    private String value;

    public static boolean contain(String value) {
        for (OrdersState ordersState : OrdersState.values()) {
            if (Objects.equals(ordersState.value, value)) {
                return true;
            }
        }
        return false;
    }

    public static String getValue(Integer code) {
        for (OrdersState ordersState : OrdersState.values()) {
            if (Objects.equals(ordersState.code, code)) {
                return ordersState.value;
            }
        }
        return null;
    }
}
