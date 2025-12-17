package com.ap.payment;

import java.util.UUID;

public class UPIPayment implements PaymentMethod {
    private String upiId;
    private String transactionId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate UPI payment processing
        System.out.println("Processing UPI payment of $" + amount + " to " + upiId);
        this.transactionId = "UPI" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "UPI";
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
