package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Transaction;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import com.slabtech.lamexPartnerTracing.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    private StockRepository stockRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository theTransactionRepository, StockRepository theStockRepository){
        transactionRepository = theTransactionRepository;
        stockRepository = theStockRepository;
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAllByOrderByIdTransactionDesc();
    }

    @Override
    public Transaction findTransactionById(int theId) {
        return transactionRepository.findById(theId).orElse(null);
    }

    @Override
    public List<Transaction> findTransactionsByStockId(int theId) {

        return transactionRepository.findByStockIdOrderByIdTransactionDesc(theId);
    }
}
