package com.learning.microservices.currency_conversion_service.rest;

import com.learning.microservices.currency_conversion_service.client.feign.CurrencyExchangeProxy;
import com.learning.microservices.currency_conversion_service.entity.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private final RestTemplate restTemplate;

    private CurrencyExchangeProxy currencyExchangeProxy;

    private final String baseUrl;

    @Autowired
    public CurrencyConversionController(@Value("${currency-exchange.api.base.url}") String baseUrl,CurrencyExchangeProxy currencyExchangeProxy){
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
        String urlTemplate = baseUrl + "/currency-exchange/from/{from}/to/{to}";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTemplate).uriVariables(
                Map.of(
                        "from",from,
                        "to",to

                )
        );
        System.out.println(builder.toUriString());
        ResponseEntity<CurrencyConversion> responseEntity = this.restTemplate.getForEntity(builder.toUriString(), CurrencyConversion.class);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+"-rest");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){

        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from,to);
        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() +"-"+"feign");
    }

}

