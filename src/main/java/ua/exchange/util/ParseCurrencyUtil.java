package ua.exchange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public final class ParseCurrencyUtil {

    public static final Logger LOG = LoggerFactory.getLogger(ParseCurrencyUtil.class);

    private ParseCurrencyUtil() {
        throw new IllegalArgumentException("Utility class");
    }

    public static Optional<Currency> parseCurrency(String currency) {
        try {
            return Optional.ofNullable(Currency.getInstance(currency.toUpperCase()));
        } catch (IllegalArgumentException e) {
            LOG.warn("Unknown currency: " + currency);
        }
        return Optional.empty();
    }

    public static List<Currency> parseCurrencies(String currencies) {
        String[] currenciesArray = currencies.split(",");
        List<Currency> result = new ArrayList<>();
        for (String currency : currenciesArray) {
            parseCurrency(currency).ifPresent(result::add);
        }
        return result;
    }

}
