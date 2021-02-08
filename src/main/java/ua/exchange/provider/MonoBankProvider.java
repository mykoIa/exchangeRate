package ua.exchange.provider;

import com.google.gson.Gson;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Component;
import ua.exchange.model.ExchangeModel;
import ua.exchange.model.MonoBankResponse;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MonoBankProvider implements Provider {

    private static final String PROVIDER_NAME = "MonoBank";
    private static final Gson GSON = new Gson();

    @Override
    public List<ExchangeModel> getExchangeRate(String baseCurrency, List<String> currencies) {
        List<ExchangeModel> exchangeModels = new ArrayList<>();
        Integer baseCurrencyCode = Currency.getInstance(baseCurrency).getNumericCode();
        Map<Integer, String> currenciesMapping = prepareMapping(currencies);
        Long date = new Date().getTime();
        for (MonoBankResponse response : getMonoBankResponses()) {
            String currency = currenciesMapping.get(response.getCurrencyCodeA());
            if (baseCurrencyCode.equals(response.getCurrencyCodeB()) && currency != null) {
                ExchangeModel exchangeModel = new ExchangeModel(baseCurrency, currency,
                        response.getRateSell(), response.getRateBuy());
                exchangeModel.setProvider(PROVIDER_NAME);
                exchangeModel.setRequestDate(date);
                exchangeModels.add(exchangeModel);
            }
        }
        return exchangeModels;
    }

    private Map<Integer, String> prepareMapping(List<String> currencies) {
        Map<Integer, String> result = new HashMap<>();
        for (String currency : currencies) {
            result.put(Currency.getInstance(currency).getNumericCode(), currency);
        }
        return result;
    }

    private MonoBankResponse[] getMonoBankResponses() {
        HttpRequest httpRequest = HttpRequest.get("https://api.monobank.ua/bank/currency");
        HttpResponse response = httpRequest.send();
        return GSON.fromJson(response.bodyText(), MonoBankResponse[].class);
    }

}
