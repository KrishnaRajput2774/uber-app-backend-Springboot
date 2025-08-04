package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.Wallet;
import com.rk.uberApp.entities.WalletTransaction;
import com.rk.uberApp.entities.enums.TransactionMethod;
import com.rk.uberApp.entities.enums.TransactionType;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.WalletRepository;
import com.rk.uberApp.services.WalletService;
import com.rk.uberApp.services.WalletTransactionsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionsService walletTransactionsService;

    @Override
    @Transactional
    public Wallet creditMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {

        Wallet wallet = walletRepository
                .findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Double currentBalance = wallet.getBalance();
        wallet.setBalance(currentBalance + amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .wallet(wallet)
                .ride(ride)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .build();

        walletTransactionsService.createWalletTransaction(walletTransaction);




        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = walletRepository
                .findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("User not Found during Money Deduction"));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .wallet(wallet)
                .ride(ride)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .build();

        walletTransactionsService.createWalletTransaction(walletTransaction);

        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);

    }

    @Override
    public void withDrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletByID(Long id) {
        return walletRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found with Id: "+id));
    }

    @Override
    public Wallet createNewWallet(User user) {

        Wallet newWallet = new Wallet();
        newWallet.setUser(user);
        return walletRepository.save(newWallet);

    }

    @Override
    public Wallet findByUser(User user) {

        return walletRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Wallet Not Found"));
    }

}
