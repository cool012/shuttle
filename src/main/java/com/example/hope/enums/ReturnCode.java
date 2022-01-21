package com.example.hope.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ReturnCode {

    OK(1, "OK"),
    NO(0, "NO");

    private Integer code;
    private String value;

    public static boolean contain(String value) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (Objects.equals(returnCode.value, value)) {
                return true;
            }
        }
        return false;
    }

    public static String getValue(Integer code) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (Objects.equals(returnCode.code, code)) {
                return returnCode.value;
            }
        }
        return null;
    }
}
