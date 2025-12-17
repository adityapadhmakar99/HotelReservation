package com.ap.model;

import com.ap.constant.PaymentType;
import com.ap.constant.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentBuilder {
    private String id;
    private double amount;
    private PaymentStatus status;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;
    private String transactionId;

    public PaymentBuilder(double amount, PaymentType paymentType) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public PaymentBuilder withStatus(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public PaymentBuilder withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Payment build() {
        Payment payment = new Payment(amount, paymentType);
        payment.setStatus(status);
        if (transactionId != null) {
            payment.setTransactionId(transactionId);
        }
        return payment;
    }
}
