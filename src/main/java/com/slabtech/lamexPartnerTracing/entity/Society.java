package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "society_name")
    private String societyName;

    @Column(name = "society_adress")
    private String societyAdress;

    @OneToMany(mappedBy = "society")
    private List<Receipt> receipts;

    public Society() {
    }

    public Society(String societyName, String societyAdress) {
        this.societyName = societyName;
        this.societyAdress = societyAdress;
    }

    public Society(int id, String societyName, String societyAdress) {
        this.id = id;
        this.societyName = societyName;
        this.societyAdress = societyAdress;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getSocietyAdress() {
        return societyAdress;
    }

    public void setSocietyAdress(String societyAdress) {
        this.societyAdress = societyAdress;
    }

    @Override
    public String toString() {
        return "Society{" +
                "id=" + id +
                ", societyName='" + societyName + '\'' +
                ", societyAdress='" + societyAdress + '\'' +
                '}';
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}

