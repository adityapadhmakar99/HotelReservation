package com.ap.model;



import com.ap.constant.PaymentType;
import com.ap.constant.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private String id;
    private double amount;
    private PaymentStatus status;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;
    private String transactionId;

    public Payment(double amount, PaymentType paymentType) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    // Getters and setters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public PaymentType getPaymentMethod() { return paymentType; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getTransactionId() { return transactionId; }

    public void setStatus(PaymentStatus status) { this.status = status; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
