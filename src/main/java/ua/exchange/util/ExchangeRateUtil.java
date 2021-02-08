package ua.exchange.util;

import ua.exchange.model.ExchangeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExchangeRateUtil {

    private ExchangeRateUtil() {
        throw new IllegalArgumentException("Utility class");
    }

    public static List<ExchangeModel> getAverageList(List<ExchangeModel> exchangeRateList) {
        Map<String, List<ExchangeModel>> sortedRates = ExchangeRateUtil.sortExchangeRates(exchangeRateList);
        return ExchangeRateUtil.calculateAverage(sortedRates);
    }


    public static Map<String, List<ExchangeModel>> sortExchangeRates(List<ExchangeModel> exchangeRates) {
        Map<String, List<ExchangeModel>> result = new HashMap<>();
        for (ExchangeModel exchangeModel : exchangeRates) {
            String key = exchangeModel.getCurrencyExchanging();
            List<ExchangeModel> list = result.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(exchangeModel);
        }
        return result;
    }

    public static List<ExchangeModel> calculateAverage(Map<String, List<ExchangeModel>> mapExchangeModels) {
        List<ExchangeModel> result = new ArrayList<>();
        for (List<ExchangeModel> list : mapExchangeModels.values()) {
            result.add(new ExchangeModel(list.get(0).getBaseCurrency(),
                    list.get(0).getCurrencyExchanging(),
                    getAverageSelling(list),
                    getAveragePurchase(list)));
        }
        return result;
    }

    public static double getAveragePurchase(List<ExchangeModel> list) {
        double result = 0.0;
        for (ExchangeModel exchange : list) {
            result += exchange.getPurchase();
        }
        return result / list.size();
    }

    public static double getAverageSelling(List<ExchangeModel> list) {
        double result = 0.0;
        for (ExchangeModel exchange : list) {
            result += exchange.getSelling();
        }
        return result / list.size();
    }

}
