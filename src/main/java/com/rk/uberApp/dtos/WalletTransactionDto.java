package com.rk.uberApp.dtos;

import com.rk.uberApp.entities.enums.TransactionMethod;
import com.rk.uberApp.entities.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {

    private Long id;

    private WalletDto wallet;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private LocalDateTime timeStamp;

}
