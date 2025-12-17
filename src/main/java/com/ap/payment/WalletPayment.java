package com.ap.payment;

import java.util.UUID;

public class WalletPayment implements PaymentMethod {
    private String walletId;
    private String transactionId;

    public WalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate wallet payment processing
        System.out.println("Processing wallet payment of $" + amount + " from wallet " + walletId);
        this.transactionId = "WALLET" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Digital Wallet";
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
