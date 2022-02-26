package com.omf.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PaymentCongiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

