package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Guichet {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID idGuichet;

    @Column(name = "code_guichet")
    private String codeGuichet;

    @Column(name = "adress_guichet")
    private String adressGuichet;

    @ManyToOne
    private Partner partner;

    @OneToMany(mappedBy = "guichet")
    private List<User> users;

    @OneToMany(mappedBy = "guichet")
    private List<Stock> stocks;

//    @OneToMany(mappedBy = "guichet")
//    private List<Stock> stocks;

    public UUID getIdGuichet() {
        return idGuichet;
    }

    public void setIdGuichet(UUID idGuichet) {
        this.idGuichet = idGuichet;
    }

    public String getCodeGuichet() {
        return codeGuichet;
    }

    public void setCodeGuichet(String codeGuichet) {
        this.codeGuichet = codeGuichet;
    }

    public String getAdressGuichet() {
        return adressGuichet;
    }

    public void setAdressGuichet(String adressGuichet) {
        this.adressGuichet = adressGuichet;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Guichet(UUID idGuichet) {
        this.idGuichet = idGuichet;
    }

    public Guichet() {
    }

    public Guichet(UUID idGuichet, String codeGuichet, String adressGuichet, Partner partner, List<User> users, List<Stock> stocks) {
        this.idGuichet = idGuichet;
        this.codeGuichet = codeGuichet;
        this.adressGuichet = adressGuichet;
        this.partner = partner;
        this.users = users;
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Guichet{" +
                "idGuichet=" + idGuichet +
                ", codeGuichet='" + codeGuichet + '\'' +
                ", adressGuichet='" + adressGuichet + '\'' +
                ", partner=" + partner +
                ", users=" + users +
                ", stocks=" + stocks +
                '}';
    }
}
