package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Payment;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.enums.PaymentStatus;
import com.rk.uberApp.entities.enums.TransactionMethod;
import com.rk.uberApp.repositories.PaymentRepository;
import com.rk.uberApp.services.PaymentService;
import com.rk.uberApp.services.WalletService;
import com.rk.uberApp.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(),
                null,
                payment.getRide(),
                TransactionMethod.RIDE);

        Double driversCut = payment.getAmount() - (1 - PLATFORM_COMMISSION);

        walletService.creditMoneyToWallet(driver.getUser(),
                driversCut,
                null,
                payment.getRide(),
                TransactionMethod.RIDE);


        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
