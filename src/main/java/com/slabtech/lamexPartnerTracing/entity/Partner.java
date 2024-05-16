package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartner;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "partner_email")
    private String partnerEmail;

    @Column(name = "partner_address")
    private String partnerAddress;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "partner_phone")
    private String partnerPhone;

    @OneToMany(mappedBy = "partner")
    private List<Stock> stocks;

    @OneToMany(mappedBy = "partner")
    private List<Transaction> transactions;

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Partner() {
    }

    public Partner(int idPartner) {
        this.idPartner = idPartner;
    }

    public Partner(int idPartner, String partnerName, String partnerEmail, String partnerAddress, boolean enabled, String partnerPhone, List<Stock> stocks, List<Transaction> transactions) {
        this.idPartner = idPartner;
        this.partnerName = partnerName;
        this.partnerEmail = partnerEmail;
        this.partnerAddress = partnerAddress;
        this.enabled = enabled;
        this.partnerPhone = partnerPhone;
        this.stocks = stocks;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "idPartner=" + idPartner +
                ", partnerName='" + partnerName + '\'' +
                ", partnerEmail='" + partnerEmail + '\'' +
                ", partnerAddress='" + partnerAddress + '\'' +
                ", enabled=" + enabled +
                ", partnerPhone='" + partnerPhone + '\'' +
                ", stocks=" + stocks +
                ", transactions=" + transactions +
                '}';
    }
}
