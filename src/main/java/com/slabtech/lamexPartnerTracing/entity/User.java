package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import javax.mail.Part;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username")
    private String userName;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @ManyToOne
    private Partner partner;

    @ManyToOne
    private Guichet guichet;


    public User() {
    }

    public User(String userName, String name, String password, boolean enabled, List<Transaction> transactions, Partner partner) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.transactions = transactions;
        this.partner = partner;
    }

    public User(UUID id, String userName, String name, String password, boolean enabled, Collection<Role> roles, List<Transaction> transactions, Partner partner, Guichet guichet) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.transactions = transactions;
        this.partner = partner;
        this.guichet = guichet;
    }

    public Guichet getGuichet() {
        return guichet;
    }

    public void setGuichet(Guichet guichet) {
        this.guichet = guichet;
    }

    public User(String userName, String name, String password, boolean enabled,
                Collection<Role> roles) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
