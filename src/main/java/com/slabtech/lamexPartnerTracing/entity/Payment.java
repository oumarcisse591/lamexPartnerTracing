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

    @Column(name = "photo")
    private String photo;

    @Column(name = "remark")
    private String remark;

    @Column(name = "currency_payment")
    private String currencyPayment;

    public String getCurrencyPayment() {
        return currencyPayment;
    }

    public void setCurrencyPayment(String currencyPayment) {
        this.currencyPayment = currencyPayment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Payment() {
    }

    public Payment(String clientName) {
        this.clientName = clientName;
    }

    public Payment(String clientName, String clientIdCard, String clientAddress, String clientPhone, String photo, String remark) {
        this.clientName = clientName;
        this.clientIdCard = clientIdCard;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
        this.photo = photo;
        this.remark = photo;
    }

    public Payment(int idTransaction, String referenceTransaction, String transactionType, Date transactionDate, String reason, double transactionAmount, String signature, String signatureAgent, Stock stock, Partner partner, User user, String clientName, String clientIdCard, String clientAddress, String clientPhone, String photo, String remark, String currencyPayment) {
        super(idTransaction, referenceTransaction, transactionType, transactionDate, reason, transactionAmount, signature, signatureAgent, stock, partner, user);
        this.clientName = clientName;
        this.clientIdCard = clientIdCard;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
        this.photo = photo;
        this.remark = remark;
        this.currencyPayment = currencyPayment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "clientName='" + clientName + '\'' +
                ", clientIdCard='" + clientIdCard + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", photo='" + photo + '\'' +
                ", remark='" + remark + '\'' +
                ", currencyPayment='" + currencyPayment + '\'' +
                '}';
    }
}
