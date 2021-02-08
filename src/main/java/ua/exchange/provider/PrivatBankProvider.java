package ua.exchange.provider;

import com.google.gson.Gson;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Component;
import ua.exchange.model.ExchangeModel;
import ua.exchange.model.PrivatBankResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PrivatBankProvider implements Provider {

    private static final String PROVIDER_NAME = "Privat Bank";
    private static final Gson GSON = new Gson();

    @Override
    public List<ExchangeModel> getExchangeRate(String baseCurrency, List<String> currencies) {
        List<ExchangeModel> exchangeModels = new ArrayList<>();
        Long date = new Date().getTime();
        for (PrivatBankResponse response : getPrivatBankResponses()) {
            if (baseCurrency.equals(response.getBase_ccy()) && currencies.contains(response.getCcy())) {
                ExchangeModel exchangeModel = new ExchangeModel(response.getBase_ccy(), response.getCcy(),
                        response.getSale(), response.getBuy());
                exchangeModel.setProvider(PROVIDER_NAME);
                exchangeModel.setRequestDate(date);
                exchangeModels.add(exchangeModel);
            }
        }
        return exchangeModels;
    }

    private PrivatBankResponse[] getPrivatBankResponses() {
        HttpRequest httpRequest = HttpRequest.get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
        HttpResponse response = httpRequest.send();
        return GSON.fromJson(response.bodyText(), PrivatBankResponse[].class);
    }

}


