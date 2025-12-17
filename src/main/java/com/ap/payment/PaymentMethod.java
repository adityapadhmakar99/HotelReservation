package com.ap.payment;

public interface PaymentMethod {
    boolean processPayment(double amount);
    String getPaymentMethodName();
    String getTransactionId();
}
