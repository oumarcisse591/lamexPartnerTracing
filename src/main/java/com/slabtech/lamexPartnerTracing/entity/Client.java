package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "code_client")
    private String codeClient;

    @Column(name = "numero_de_compte")
    private String numeroDeCompte;

    @Column(name = "banque")
    private String Banque;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "client")
    private List<Receipt> receipts;

    public Client() {
    }

    public Client(String nom, String adresse, String codeClient, String numeroDeCompte, String banque, boolean enabled, List<Receipt> receipts) {
        this.nom = nom;
        this.adresse = adresse;
        this.codeClient = codeClient;
        this.numeroDeCompte = numeroDeCompte;
        Banque = banque;
        this.enabled = enabled;
        this.receipts = receipts;
    }

    public Client(int id, String nom, String adresse, String codeClient, String numeroDeCompte, String banque, boolean enabled, List<Receipt> receipts) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.codeClient = codeClient;
        this.numeroDeCompte = numeroDeCompte;
        Banque = banque;
        this.enabled = enabled;
        this.receipts = receipts;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getNumeroDeCompte() {
        return numeroDeCompte;
    }

    public void setNumeroDeCompte(String numeroDeCompte) {
        this.numeroDeCompte = numeroDeCompte;
    }

    public String getBanque() {
        return Banque;
    }

    public void setBanque(String banque) {
        Banque = banque;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", codeClient='" + codeClient + '\'' +
                ", numeroDeCompte='" + numeroDeCompte + '\'' +
                ", Banque='" + Banque + '\'' +
                '}';
    }
}

