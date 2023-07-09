package com.gateway.service_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {
    @GetMapping("/greet")
    public String hello(){
        return "Hello from Service 2";
    }
}
