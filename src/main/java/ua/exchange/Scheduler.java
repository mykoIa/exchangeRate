package ua.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ua.exchange.db.DataBaseService;
import ua.exchange.model.ExchangeModel;
import ua.exchange.provider.Provider;

import java.util.List;

@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    private List<Provider> providers;
    @Value("${providers.baseCurrency}")
    private String baseCurrency;
    @Value("#{'${providers.currencies}'.split(',')}")
    private List<String> currencies;

    @Autowired
    private DataBaseService dbService;

    public static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(cron = "${scheduler.cron}")
    public void fillingDataFromProviders() {
        for (Provider provider : providers) {
            try {
                saveToDB(provider.getExchangeRate(baseCurrency, currencies));
            } catch (Exception e) {
                LOG.warn("Error getting data from provider: {}", provider.getClass().getSimpleName(), e);
            }
        }
    }

    private void saveToDB(List<ExchangeModel> averageRates) {
        for (ExchangeModel model : averageRates) {
            dbService.addModelToDataBase(model);
        }
    }

}
