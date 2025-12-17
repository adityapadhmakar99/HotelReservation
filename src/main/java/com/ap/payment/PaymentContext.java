package com.ap.payment;

import com.ap.constant.PaymentStatus;
import com.ap.constant.PaymentType;
import com.ap.model.Payment;
import com.ap.model.PaymentBuilder;

public class PaymentContext {
    private PaymentMethod paymentMethod;

    public PaymentContext(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment executePayment(double amount) {
        boolean paymentSuccess = paymentMethod.processPayment(amount);
        
        if (paymentSuccess) {
            System.out.println("Payment successful via " + paymentMethod.getPaymentMethodName());
            System.out.println("Transaction ID: " + paymentMethod.getTransactionId());
            
            return new PaymentBuilder(amount, PaymentType.valueOf(paymentMethod.getPaymentMethodName().toUpperCase()))
                .withStatus(PaymentStatus.COMPLETED)
                .withTransactionId(paymentMethod.getTransactionId())
                .build();
        } else {
            System.out.println("Payment failed via " + paymentMethod.getPaymentMethodName());
            return new PaymentBuilder(amount, PaymentType.valueOf(paymentMethod.getPaymentMethodName().toUpperCase()))
                .withStatus(PaymentStatus.FAILED)
                .build();
        }
    }
}
