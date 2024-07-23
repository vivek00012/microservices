package com.learning.microservices.currency_exchange_service.rest;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class CircuitBreakerController {
    private final RestTemplate restTemplate = new RestTemplate();
    private Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/sample-api")
//    @Retry(name ="sample-api",fallbackMethod = "hardcodedResponse")
    //@CircuitBreaker(name ="default",fallbackMethod = "hardcodedResponse")
//    @RateLimiter(name="default")
   // @Bulkhead(name="default")
    @Bulkhead(name="sample-api")

    public String sampleApi(){
        logger.info("Sample API call received");
//        ResponseEntity<String> responseEntity = restTemplate
//                .getForEntity("http://localhost:8080/some-dummy-url",String.class);

        return "sample api back";
    }

    public String hardcodedResponse(Exception ex){
        return "fallback-response";
    }
}
