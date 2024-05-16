package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Movement;
import com.slabtech.lamexPartnerTracing.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    List<Transaction> findByStockId(int stockId);

    List<Transaction> findByStockIdOrderByIdTransactionDesc(int stockId);
    List<Transaction> findAllByOrderByIdTransactionDesc();
}
