package com.learning.microservices.currency_exchange_service.rest;

import com.learning.microservices.currency_exchange_service.entity.CurrencyExchange;
import com.learning.microservices.currency_exchange_service.repository.ICurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Environment environment;
    private ICurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    public CurrencyExchangeController(Environment environment,ICurrencyExchangeRepository currencyExchangeRepository){
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) throws  RuntimeException{
        CurrencyExchange currencyExchange = this.currencyExchangeRepository.findByFromAndTo(from,to);
        if(currencyExchange ==null){
            throw new RuntimeException("Unable to find data for " + from+" to "+ to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;

    }
}
