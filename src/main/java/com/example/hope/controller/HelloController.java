package com.example.hope.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.hope.common.utils.Utils;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

@RestController
@Api(tags = "测试相关接口")
public class HelloController {

    @GetMapping("/hello")
    public String Hello() {
        return Utils.encode("hope");
    }

    @GetMapping("/test")
    public String test() {
        JSONObject jsonObject = new RestTemplate().getForEntity("http://ip-api.com/json", JSONObject.class).getBody();
        return jsonObject.getStr("query");
    }

    @PostMapping("/json")
    public String json(String text) {
        System.out.println(text);
        return text;
    }

    @GetMapping("/query")
    public String textXSS(String text) {
        return text;
    }

    @GetMapping("/map")
    public void map(@RequestParam Map<String, String> map) {
        System.out.println(map.get("user"));
    }
}