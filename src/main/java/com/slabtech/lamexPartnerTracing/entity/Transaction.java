package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_transaction",
        discriminatorType = DiscriminatorType.INTEGER)
public class Transaction {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID idTransaction;

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

    private String signature;

    @Column(name = "signature_agent")
    private String signatureAgent;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Partner partner;

    @ManyToOne
    private User user;

    public UUID getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(UUID idTransaction) {
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getSignatureAgent() {
        return signatureAgent;
    }

    public void setSignatureAgent(String signatureAgent) {
        this.signatureAgent = signatureAgent;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }

    public Transaction() {
    }

    public Transaction(UUID idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Transaction(UUID idTransaction, String referenceTransaction, String transactionType, Date transactionDate, String reason, double transactionAmount, String signature, String signatureAgent, Stock stock, Partner partner, User user) {
        this.idTransaction = idTransaction;
        this.referenceTransaction = referenceTransaction;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.reason = reason;
        this.transactionAmount = transactionAmount;
        this.stock = stock;
        this.partner = partner;
        this.user = user;
        this.signature = signature;
        this.signatureAgent = signatureAgent;
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
                ", signature=" + signature +
                ", signatureAgent=" + signatureAgent +
                ", stock=" + stock +
                ", partner=" + partner +
                ", user=" + user +
                '}';
    }
}
