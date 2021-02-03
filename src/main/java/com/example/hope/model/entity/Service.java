package com.example.hope.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class Service implements Serializable {

    private long id;
    private String name;
    private String color;
    private String icon;

}