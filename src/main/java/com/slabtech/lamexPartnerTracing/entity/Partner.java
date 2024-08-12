package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Partner {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID idPartner;

    @Column(name = "partner_code")
    private String partnerCode;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "partner_email")
    private String partnerEmail;

    @Column(name = "partner_country")
    private String partnerCountry;
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

    @OneToMany(mappedBy = "partner")
    private List<User> users;

    @OneToMany(mappedBy = "partner")
    private List<Guichet> guichets;

    public String getPartnerCountry() {
        return partnerCountry;
    }

    public void setPartnerCountry(String partnerCountry) {
        this.partnerCountry = partnerCountry;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public UUID getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(UUID idPartner) {
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

    public Partner(UUID idPartner) {
        this.idPartner = idPartner;
    }

    public Partner(UUID idPartner, String partnerName, String partnerCode, String partnerEmail, String partnerAddress, boolean enabled, String partnerPhone, List<Stock> stocks, List<Transaction> transactions, String partnerCountry, List<User> users) {
        this.idPartner = idPartner;
        this.partnerName = partnerName;
        this.partnerCode = partnerCode;
        this.partnerEmail = partnerEmail;
        this.partnerAddress = partnerAddress;
        this.enabled = enabled;
        this.partnerPhone = partnerPhone;
        this.stocks = stocks;
        this.transactions = transactions;
        this.partnerCountry = partnerCountry;
        this.users = users;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "idPartner=" + idPartner +
                ", partnerName='" + partnerName + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                ", partnerEmail='" + partnerEmail + '\'' +
                ", partnerAddress='" + partnerAddress + '\'' +
                ", enabled=" + enabled +
                ", partnerPhone='" + partnerPhone + '\'' +
                ", stocks=" + stocks +
                ", transactions=" + transactions +
                '}';
    }
}
