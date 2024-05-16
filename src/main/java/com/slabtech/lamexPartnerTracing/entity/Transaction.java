package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_transaction",
        discriminatorType = DiscriminatorType.INTEGER)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransaction;

    @Column(name = "reference_transaction")
    private String referenceTransaction;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "transaction_amount")
    private double transactionAmount;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Partner partner;

    @ManyToOne
    private User user;

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceTransaction() {
        return referenceTransaction;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }

    public Transaction() {
    }

    public Transaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Transaction(int idTransaction, String referenceTransaction, String transactionType, Date transactionDate, String reason, double transactionAmount, Stock stock, Partner partner, User user) {
        this.idTransaction = idTransaction;
        this.referenceTransaction = referenceTransaction;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.reason = reason;
        this.transactionAmount = transactionAmount;
        this.stock = stock;
        this.partner = partner;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "idTransaction=" + idTransaction +
                "referenceTransaction=" + referenceTransaction +
                "transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                ", reason='" + reason + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", stock=" + stock +
                ", partner=" + partner +
                ", user=" + user +
                '}';
    }
}
