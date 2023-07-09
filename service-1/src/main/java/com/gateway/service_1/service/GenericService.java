package com.gateway.service_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class GenericService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    public String getGreetingFromAnotherService(){
        return restTemplate.getForObject("http://SERVICE2/greet", String.class);
    }
}
