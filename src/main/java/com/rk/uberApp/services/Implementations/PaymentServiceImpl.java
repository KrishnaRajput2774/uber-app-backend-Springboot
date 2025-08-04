package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.entities.Payment;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.enums.PaymentStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.PaymentRepository;
import com.rk.uberApp.services.PaymentService;
import com.rk.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found for Ride with id: "+ride.getId()));

        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {

        Payment payment = Payment
                .builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .amount(ride.getFare())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {

        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);

    }
}
