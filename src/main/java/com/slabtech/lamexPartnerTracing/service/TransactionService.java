package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAllTransaction();

    Transaction findTransactionById(int theId);

    List<Transaction> findTransactionsByStockId(int theId);
}
