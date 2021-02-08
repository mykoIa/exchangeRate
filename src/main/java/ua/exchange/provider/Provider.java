package ua.exchange.provider;

import ua.exchange.model.ExchangeModel;

import java.util.List;

public interface Provider {

    List<ExchangeModel> getExchangeRate(String baseCurrency, List<String> currencies);

}
