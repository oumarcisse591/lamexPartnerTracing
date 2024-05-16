package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "receipt_code")
    private String receiptCode;

    @Column(name = "swift_code")
    private String swift_code;

    @Column(name = "receipt_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;

    @Column(name = "value_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date valueDate;

    @Column(name = "amount")
    private double amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "beneficiary_bank")
    private String beneficiaryBank;

    @Column(name = "beneficiary_account")
    private String beneficiaryAccount;

    @Column(name = "beneficiary_bank_address")
    private String beneficiaryBankAddress;

    @Column(name = "remitter_bank")
    private String remitterBank;

    @Column(name = "remitter_account")
    private String remitterAccount;

    @Column(name = "motif_transaction")
    private String motifTransaction;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "id_society")
    private Society society;

    @ManyToOne
    @JoinColumn(name = "id_beneficiary")
    private Beneficiary beneficiary;

    public Receipt() {
    }

    public Receipt(String receiptCode, String swift_code, Date receiptDate, Date valueDate, double amount, String currency, String beneficiaryBank, String beneficiaryAccount, String beneficiaryBankAddress, String remitterBank, String remitterAccount, boolean enabled, String motifTransaction, Client client, Society society, Beneficiary beneficiary) {
        this.receiptCode = receiptCode;
        this.swift_code = swift_code;
        this.receiptDate = receiptDate;
        this.valueDate = valueDate;
        this.amount = amount;
        this.currency = currency;
        this.beneficiaryBank = beneficiaryBank;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryBankAddress = beneficiaryBankAddress;
        this.remitterBank = remitterBank;
        this.remitterAccount = remitterAccount;
        this.motifTransaction = motifTransaction;
        this.enabled = enabled;
        this.client = client;
        this.society = society;
        this.beneficiary = beneficiary;
    }

    public Receipt(int id, String receiptCode, String swift_code, Date receiptDate, Date valueDate, double amount, String currency, String beneficiaryBank, String beneficiaryAccount, String beneficiaryBankAddress, String remitterBank, String remitterAccount, boolean enabled, String motifTransaction, Client client, Society society, Beneficiary beneficiary) {
        this.id = id;
        this.receiptCode = receiptCode;
        this.swift_code = swift_code;
        this.receiptDate = receiptDate;
        this.valueDate = valueDate;
        this.amount = amount;
        this.currency = currency;
        this.beneficiaryBank = beneficiaryBank;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryBankAddress = beneficiaryBankAddress;
        this.remitterBank = remitterBank;
        this.remitterAccount = remitterAccount;
        this.enabled = enabled;
        this.motifTransaction = motifTransaction;
        this.client = client;
        this.society = society;
        this.beneficiary = beneficiary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getSwift_code() {
        return swift_code;
    }

    public void setSwift_code(String swift_code) {
        this.swift_code = swift_code;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBeneficiaryBank() {
        return beneficiaryBank;
    }

    public void setBeneficiaryBank(String beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getRemitterBank() {
        return remitterBank;
    }

    public void setRemitterBank(String remitterBank) {
        this.remitterBank = remitterBank;
    }

    public String getRemitterAccount() {
        return remitterAccount;
    }

    public void setRemitterAccount(String remitterAccount) {
        this.remitterAccount = remitterAccount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getMotifTransaction() {
        return motifTransaction;
    }

    public void setMotifTransaction(String motifTransaction) {
        this.motifTransaction = motifTransaction;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getBeneficiaryBankAddress() {
        return beneficiaryBankAddress;
    }

    public void setBeneficiaryBankAddress(String beneficiaryBankAddress) {
        this.beneficiaryBankAddress = beneficiaryBankAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return enabled;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", receiptCode='" + receiptCode + '\'' +
                ", swift_code='" + swift_code + '\'' +
                ", receiptDate=" + receiptDate +
                ", valueDate = " + valueDate +
                ", amount=" + amount +
                ", currency=" + currency +
                ", beneficiaryBank='" + beneficiaryBank + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", remitterBank='" + remitterBank + '\'' +
                ", remitterAccount='" + remitterAccount + '\'' +
                ", client=" + client +
                ", society=" + society +
                '}';
    }
}

