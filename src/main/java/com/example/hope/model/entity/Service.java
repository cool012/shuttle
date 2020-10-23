package com.example.hope.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Service implements Serializable {

    private long id;

    private String service_name;

    public Service(String service_name) {
        this.service_name = service_name;
    }
}