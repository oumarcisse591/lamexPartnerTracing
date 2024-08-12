package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stock_description")
    private String stockDescription;

    @Column(name = "balance")
    private double balance;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    private Partner partner;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "stock")
    private List<Transaction> transactions;

    @ManyToOne
    private Guichet guichet;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getStockDescription() {
        return stockDescription;
    }

    public void setStockDescription(String stockDescription) {
        this.stockDescription = stockDescription;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Stock() {
    }

    public Stock(UUID id) {
        this.id = id;
    }

    public Stock(UUID id, String stockName, String stockDescription, double balance, Date createdAt, Partner partner, boolean enabled, Country country, List<Transaction> transactions) {
        this.id = id;
        this.stockName = stockName;
        this.stockDescription = stockDescription;
        this.balance = balance;
        this.createdAt = createdAt;
        this.partner = partner;
        this.enabled = enabled;
        this.transactions = transactions;
        this.country =country;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockName='" + stockName + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", partner=" + partner +
                ", enabled=" + enabled +
                ", stockDescription=" + stockDescription+
                ", country=" + country +
                ", transactions=" + transactions +
                '}';
    }
}
