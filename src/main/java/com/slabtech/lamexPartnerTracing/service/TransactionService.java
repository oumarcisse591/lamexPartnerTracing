package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> findAllTransaction();

    Transaction findTransactionById(UUID theId);

    List<Transaction> findTransactionsByStockId(UUID theId);

    List<Transaction> getLastsTenTransactions(UUID idStock);
}
