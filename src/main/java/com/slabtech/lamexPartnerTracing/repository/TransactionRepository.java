package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Movement;
import com.slabtech.lamexPartnerTracing.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
    List<Transaction> findByStockId(UUID stockId);

    @Query("SELECT COALESCE(SUM(t.transactionAmount), 0.0) FROM Transaction t WHERE t.stock.id = :stockId AND t.transactionType = :type")
    Double sumAmountByStockIdAndType(UUID stockId, String type);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.stock.id = :stockId AND t.transactionType = :type")
    Long countTransactionsByStockIdAndType(UUID stockId, String type);

    List<Transaction> findByStockIdOrderByIdTransactionDesc(UUID stockId);
    List<Transaction> findAllByOrderByIdTransactionDesc();

    Page<Transaction> findByStockIdOrderByIdTransactionDesc(UUID stockId, Pageable pageable);
}
