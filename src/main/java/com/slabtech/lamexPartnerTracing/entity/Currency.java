package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Currency {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID idCurrency;

    @Column(name = "currency_name")
    private String CurrencyName;

    public Currency(UUID idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Currency(UUID idCurrency, String currencyName) {
        this.idCurrency = idCurrency;
        CurrencyName = currencyName;
    }

    public UUID getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(UUID idCurrency) {
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
