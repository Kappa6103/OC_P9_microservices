package com.medilabo.risk_assessment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class AbstractClient {

    @Value("${api.gateway.url}")
    protected String gatewayUrl;

    @Autowired
    protected RestTemplate restTemplate;

}
