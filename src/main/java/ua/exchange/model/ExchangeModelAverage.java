package ua.exchange.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ExchangeModelAverage {

    @Column(name = "selling")
    private final double selling;

    @Column(name = "purchase")
    private final double purchase;

    public ExchangeModelAverage(double selling, double purchase) {
        this.selling = selling;
        this.purchase = purchase;
    }

    public double getSelling() {
        return selling;
    }

    public double getPurchase() {
        return purchase;
    }

}
