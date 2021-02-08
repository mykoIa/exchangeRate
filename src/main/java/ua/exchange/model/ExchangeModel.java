package ua.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency_exchange")
public class ExchangeModel {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "currency_exchanging")
    private String currencyExchanging;

    @Column(name = "base_currency")
    private String baseCurrency;

    @Column(name = "date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestDate;

    @Column(name = "selling")
    private double selling;

    @Column(name = "purchase")
    private double purchase;

    @Column(name = "provider")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String provider;

    public ExchangeModel() {
    }

    public ExchangeModel(String baseCurrency, String currencyExchanging, double selling, double purchase) {
        this.currencyExchanging = currencyExchanging;
        this.baseCurrency = baseCurrency;
        this.selling = selling;
        this.purchase = purchase;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyExchanging() {
        return currencyExchanging;
    }

    public void setCurrencyExchanging(String currencyExchanging) {
        this.currencyExchanging = currencyExchanging;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String currencyReceive) {
        this.baseCurrency = currencyReceive;
    }

    public double getSelling() {
        return selling;
    }

    public void setSelling(double selling) {
        this.selling = selling;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public Long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Long requestDate) {
        this.requestDate = requestDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
