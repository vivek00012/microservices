package com.learning.microservices.currency_conversion_service.client.feign;

import com.learning.microservices.currency_conversion_service.entity.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name ="currency-exchange",url="${currency-exchange.api.base.url}")
@FeignClient(name ="currency-exchange")

public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
     CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to
    );
}
