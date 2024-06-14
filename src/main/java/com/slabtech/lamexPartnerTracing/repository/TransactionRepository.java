package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Movement;
import com.slabtech.lamexPartnerTracing.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    List<Transaction> findByStockId(int stockId);

    @Query("SELECT SUM(t.transactionAmount) FROM Transaction t WHERE t.stock.id = :stockId AND t.transactionType = :type")
    Double sumAmountByStockIdAndType(int stockId, String type);

    List<Transaction> findByStockIdOrderByIdTransactionDesc(int stockId);
    List<Transaction> findAllByOrderByIdTransactionDesc();
}
