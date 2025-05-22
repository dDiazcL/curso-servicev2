package com.edutech.curse_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class appConfig {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
