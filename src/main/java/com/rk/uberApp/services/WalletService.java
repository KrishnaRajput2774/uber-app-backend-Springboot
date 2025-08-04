package com.rk.uberApp.services;

import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.Wallet;
import com.rk.uberApp.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet creditMoneyToWallet(User user,
                               Double amount,
                               String transactionId,
                               Ride ride,
                               TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user,
                                 Double amount,
                                 String transactionId,
                                 Ride ride,
                                 TransactionMethod transactionMethod);

    void withDrawAllMyMoneyFromWallet();

    Wallet findWalletByID(Long id);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);

}
