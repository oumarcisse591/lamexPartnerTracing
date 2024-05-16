package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "beneficiary_adress")
    private String beneficiaryAdress;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "beneficiary")
    private List<Receipt> receipts;

    public Beneficiary() {
    }

    public Beneficiary(String beneficiaryName, String beneficiaryAdress, boolean enabled, List<Receipt> receipts) {
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAdress = beneficiaryAdress;
        this.enabled = enabled;
        this.receipts = receipts;
    }

    public Beneficiary(int id, String beneficiaryName, String beneficiaryAdress, boolean enabled, List<Receipt> receipts) {
        this.id = id;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAdress = beneficiaryAdress;
        this.enabled = enabled;
        this.receipts = receipts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAdress() {
        return beneficiaryAdress;
    }

    public void setBeneficiaryAdress(String beneficiaryAdress) {
        this.beneficiaryAdress = beneficiaryAdress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "id='" + id + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAdress='" + beneficiaryAdress + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

