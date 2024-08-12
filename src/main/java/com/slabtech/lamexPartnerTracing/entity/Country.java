package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Country {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID idCountry;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

//    @OneToMany(mappedBy = "country")
//    private List<Stock> stocks;

//    public List<Stock> getStocks() {
//        return stocks;
//    }
//
//    public void setStocks(List<Stock> stocks) {
//        this.stocks = stocks;
//    }

    public UUID getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(UUID idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Country() {
    }

    public Country(UUID idCountry) {
        this.idCountry = idCountry;
    }

    public Country(UUID idCountry, String countryName, String countryCode, List<Stock> stocks) {
        this.idCountry = idCountry;
        this.countryName = countryName;
        this.countryCode = countryCode;
//        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Country{" +
                "idCountry=" + idCountry +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
//                ", stocks='" + stocks + '\'' +
                '}';
    }
}
