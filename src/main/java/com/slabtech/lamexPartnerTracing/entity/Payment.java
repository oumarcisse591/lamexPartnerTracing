package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@DiscriminatorValue("1")
public class Payment extends Transaction{

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_idCard")
    private String clientIdCard;

    @Column(name = "client_address")
    private String clientAddress;

    @Column(name = "client_phone")
    private String clientPhone;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIdCard() {
        return clientIdCard;
    }

    public void setClientIdCard(String clientIdCard) {
        this.clientIdCard = clientIdCard;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Payment() {
    }

    public Payment(String clientName) {
        this.clientName = clientName;
    }

    public Payment(String clientName, String clientIdCard, String clientAddress, String clientPhone) {
        this.clientName = clientName;
        this.clientIdCard = clientIdCard;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }

    public Payment(int idTransaction, String referenceTransaction, String transactionType, Date transactionDate, String reason, double transactionAmount, Stock stock, Partner partner, User user, String clientName, String clientIdCard, String clientAddress, String clientPhone) {
        super(idTransaction, referenceTransaction, transactionType, transactionDate, reason, transactionAmount, stock, partner, user);
        this.clientName = clientName;
        this.clientIdCard = clientIdCard;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "clientName='" + clientName + '\'' +
                ", clientIdCard='" + clientIdCard + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                '}';
    }
}
