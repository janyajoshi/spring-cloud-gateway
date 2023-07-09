package com.gateway.api_gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/auth")
    public Mono<String> authFallback(){
        return Mono.just("Auth Service not available. Please try later.");
    }

    @RequestMapping("/service1")
    public Mono<String> service1Fallback(){
        return Mono.just("Service 1 not available. please try later.");
    }

    @RequestMapping("/service2")
    public Mono<String> service2Fallback(){
        return Mono.just("Service 2 not available. please try later.");
    }

}
