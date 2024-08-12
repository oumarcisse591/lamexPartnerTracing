package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Transaction;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import com.slabtech.lamexPartnerTracing.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public Transaction findTransactionById(UUID theId) {
        return transactionRepository.findById(theId).orElse(null);
    }

    @Override
    public List<Transaction> findTransactionsByStockId(UUID theId) {

        return transactionRepository.findByStockIdOrderByIdTransactionDesc(theId);
    }

    @Override
    public List<Transaction> getLastsTenTransactions(UUID idStock) {
        return transactionRepository.findByStockIdOrderByIdTransactionDesc(idStock, PageRequest.of(0, 10)).getContent();
    }
}
