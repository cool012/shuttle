package com.example.hope.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试")
public class HelloController {

    @GetMapping("/hello")
    public void Hello(){
        int x =  1 / 0;
    }


    @PostMapping("/test")
    public String test(String text){
        System.out.println(text);
        return text;
    }

    @PostMapping("/json")
    public String json(String text){
        System.out.println(text);
        return text;
    }

    @GetMapping("/query")
    public String textXSS(String text){
        System.out.println(text);
        return text;
    }
}