package com.afanty.afantyorder.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/test")
public class Test {

    @RequestMapping("index")
    private String sayHi(){
        return "hello afanty";
    }
}
