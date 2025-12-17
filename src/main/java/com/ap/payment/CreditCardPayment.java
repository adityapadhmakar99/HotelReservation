package com.ap.payment;

import java.util.UUID;

public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String transactionId;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate payment processing
        System.out.println("Processing credit card payment of $" + amount);
        // In a real implementation, this would integrate with a payment gateway
        this.transactionId = "CC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit_Card";
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
