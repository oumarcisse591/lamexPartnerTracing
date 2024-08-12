package com.slabtech.lamexPartnerTracing.dto;

import com.slabtech.lamexPartnerTracing.entity.Role;

import java.util.Collection;

public class PartnerUserDto {

    private String partnerName;
    private String partnerEmail;
    private String partnerCountry;
    private String partnerAdress;
    private String partnerPhone;
    private String userName;
    private String name;
    private String password;
    private Collection<Role> role;

    private String codeGuichet;

    private String adresseGuichet;

    public PartnerUserDto(String partnerName, String partnerEmail, String partnerCountry, String partnerAdress, String partnerPhone, String userName, String name, String password, Collection<Role> role) {
        this.partnerName = partnerName;
        this.partnerEmail = partnerEmail;
        this.partnerCountry = partnerCountry;
        this.partnerAdress = partnerAdress;
        this.partnerPhone = partnerPhone;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public PartnerUserDto(String partnerName, String partnerEmail, String partnerCountry, String partnerAdress, String partnerPhone, String userName, String name, String password, Collection<Role> role, String codeGuichet, String adresseGuichet) {
        this.partnerName = partnerName;
        this.partnerEmail = partnerEmail;
        this.partnerCountry = partnerCountry;
        this.partnerAdress = partnerAdress;
        this.partnerPhone = partnerPhone;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.role = role;
        this.codeGuichet = codeGuichet;
        this.adresseGuichet = adresseGuichet;
    }

    public String getCodeGuichet() {
        return codeGuichet;
    }

    public void setCodeGuichet(String codeGuichet) {
        this.codeGuichet = codeGuichet;
    }

    public String getAdresseGuichet() {
        return adresseGuichet;
    }

    public void setAdresseGuichet(String adresseGuichet) {
        this.adresseGuichet = adresseGuichet;
    }

    public PartnerUserDto() {
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

    public String getPartnerCountry() {
        return partnerCountry;
    }

    public void setPartnerCountry(String partnerCountry) {
        this.partnerCountry = partnerCountry;
    }

    public String getPartnerAdress() {
        return partnerAdress;
    }

    public void setPartnerAdress(String partnerAdress) {
        this.partnerAdress = partnerAdress;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRole() {
        return role;
    }

    public void setRole(Collection<Role> role) {
        this.role = role;
    }
}
