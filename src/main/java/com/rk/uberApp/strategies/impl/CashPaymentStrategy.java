package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Payment;
import com.rk.uberApp.entities.enums.PaymentStatus;
import com.rk.uberApp.entities.enums.TransactionMethod;
import com.rk.uberApp.repositories.PaymentRepository;
import com.rk.uberApp.services.PaymentService;
import com.rk.uberApp.services.WalletService;
import com.rk.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();
        Double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(),
                platformCommission,
                null,
                payment.getRide(),
                TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}