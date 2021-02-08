package ua.exchange.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import ua.exchange.model.ExchangeModel;
import ua.exchange.model.ExchangeModelAverage;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class DataBaseService {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public void addModelToDataBase(ExchangeModel exchangeModel) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(exchangeModel);
            session.getTransaction().commit();
        }
    }

    public List<ExchangeModel> findLastEntry(String baseCurrency, List<Currency> currencies) {
        List<ExchangeModel> result = new ArrayList<>();
        for (Currency currency : currencies) {
            result.addAll(getCurrencyRate(baseCurrency, currency.getCurrencyCode()));
        }
        return result;
    }

    public List<ExchangeModel> findByTimeRange(String baseCurrency, List<Currency> currencies,
                                               Long dateFrom, Long dateTo) {
        List<ExchangeModel> result = new ArrayList<>();
        for (Currency currency : currencies) {
            result.add(getCurrencyRateByTimeRange(baseCurrency, currency.getCurrencyCode(), dateFrom, dateTo));
        }
        return result;
    }

    private List<ExchangeModel> getCurrencyRate(String baseCurrency, String currency) {
        try (final Session session = factory.openSession()) {
            Query<ExchangeModel> query = session.createQuery("from ExchangeModel rates " +
                            "where base_currency = :base_currency AND currency_exchanging = :currency_exchanging " +
                            "AND date = (select max(requestDate) from ExchangeModel where provider = rates.provider)",
                    ExchangeModel.class)
                    .setParameter("base_currency", baseCurrency)
                    .setParameter("currency_exchanging", currency);
            return query.getResultList();
        }
    }

    private ExchangeModel getCurrencyRateByTimeRange(String baseCurrency, String currency, Long dateFrom, Long dateTo) {
        try (final Session session = factory.openSession()) {
            Query<ExchangeModelAverage> query = session.createQuery("select " +
                    "new ua.exchange.model.ExchangeModelAverage(AVG(selling), AVG(purchase)) " +
                    "from ExchangeModel where base_currency = :base_currency AND currency_exchanging " +
                    "= :currency_exchanging AND date > :date_from AND date < :date_to", ExchangeModelAverage.class)
                    .setParameter("base_currency", baseCurrency)
                    .setParameter("currency_exchanging", currency)
                    .setParameter("date_from", dateFrom)
                    .setParameter("date_to", dateTo);
            ExchangeModelAverage result = query.getSingleResult();
            return new ExchangeModel(baseCurrency, currency, result.getSelling(), result.getPurchase());
        }
    }

}
