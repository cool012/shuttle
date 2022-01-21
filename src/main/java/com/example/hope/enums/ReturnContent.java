package com.example.hope.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ReturnContent {

    SUCCESS(1, "success"),
    FAILURE(0, "failure");

    private Integer code;
    private String value;

    public static boolean contain(String value) {
        for (ReturnContent returnContent : ReturnContent.values()) {
            if (Objects.equals(returnContent.value, value)) {
                return true;
            }
        }
        return false;
    }

    public static String getValue(Integer code) {
        for (ReturnContent returnContent : ReturnContent.values()) {
            if (Objects.equals(returnContent.code, code)) {
                return returnContent.value;
            }
        }
        return null;
    }
}
