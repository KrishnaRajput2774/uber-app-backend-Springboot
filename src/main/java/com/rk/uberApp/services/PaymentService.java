package com.rk.uberApp.services;

import com.rk.uberApp.entities.Payment;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);

}
