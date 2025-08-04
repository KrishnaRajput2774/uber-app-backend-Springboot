package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.entities.WalletTransaction;
import com.rk.uberApp.repositories.WalletTransactionRepository;
import com.rk.uberApp.services.WalletTransactionsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionsServiceImpl implements WalletTransactionsService {


    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
