package com.learning.microservices.currency_exchange_service.repository;

import com.learning.microservices.currency_exchange_service.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {
    CurrencyExchange findByFromAndTo(String from,String to);
}
