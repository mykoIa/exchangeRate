package ua.exchange.model;

import ua.exchange.util.ExchangeRateUtil;

import java.util.List;

public class CurrenciesRateListWithAverages {

    List<ExchangeModel> exchangeRateList;
    List<ExchangeModel> averages;

    public CurrenciesRateListWithAverages(List<ExchangeModel> exchangeRateList) {
        this.exchangeRateList = exchangeRateList;
        this.averages = ExchangeRateUtil.getAverageList(exchangeRateList);
    }

    public List<ExchangeModel> getExchangeRateList() {
        return exchangeRateList;
    }

    public List<ExchangeModel> getAverages() {
        return averages;
    }

}
