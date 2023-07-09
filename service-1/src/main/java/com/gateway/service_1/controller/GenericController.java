package com.gateway.service_1.controller;

import com.gateway.service_1.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {

    @Autowired
    private GenericService genericService;

    @GetMapping("/greet")
    public String greet(@RequestHeader("loggedInUser") String userName){
        return userName + ", Hello from Service 1" ;
    }

    @GetMapping("/combined-greet")
    public String combinedGreet(@RequestHeader("loggedInUser") String userName){
        return new StringBuilder().append(userName).append(", Hello from Service 1").append(", ").append(genericService.getGreetingFromAnotherService()).toString() ;
    }
}
