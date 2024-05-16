package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCurrency;

    @Column(name = "currency_name")
    private String CurrencyName;

    public Currency(int idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Currency(int idCurrency, String currencyName) {
        this.idCurrency = idCurrency;
        CurrencyName = currencyName;
    }

    public int getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(int idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public Currency() {
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "idCurrency=" + idCurrency +
                ", CurrencyName='" + CurrencyName + '\'' +
                '}';
    }
}
