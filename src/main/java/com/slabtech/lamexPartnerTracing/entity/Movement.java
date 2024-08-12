package com.slabtech.lamexPartnerTracing.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@DiscriminatorValue("2")
public class Movement extends Transaction{

    @Column(name = "movement_title")
    private String movementTitle;

    @Column(name = "movement_code")
    private String movementCode;

    public String getMovementTitle() {
        return movementTitle;
    }

    public void setMovementTitle(String movementTitle) {
        this.movementTitle = movementTitle;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public void setMovementCode(String movementCode) {
        this.movementCode = movementCode;
    }



    public Movement() {
    }

    public Movement(String movementTitle, String movementCode) {
        this.movementTitle = movementTitle;
        this.movementCode = movementCode;
    }

    public Movement(UUID idTransaction, String movementTitle, String movementCode) {
        super(idTransaction);
        this.movementTitle = movementTitle;
        this.movementCode = movementCode;
    }

    public Movement(UUID idTransaction, String referenceTransaction, String transactionType, Date transactionDate, String reason, double transactionAmount, String signature, String signatureAgent, Stock stock, Partner partner, User user, String movementTitle, String movementCode) {
        super(idTransaction, referenceTransaction, transactionType, transactionDate, reason, transactionAmount, signature, signatureAgent, stock, partner, user);
        this.movementTitle = movementTitle;
        this.movementCode = movementCode;
    }

    @Override
    public String toString() {
        return "Movement{" +
                ", MovementTitle='" + movementTitle + '\'' +
                ", movementCode='" + movementCode + '\'' +
                '}';
    }
}
