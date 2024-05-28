package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Payment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAllByOrderByIdTransactionDesc();

    @Query("SELECT COUNT(p) FROM Payment p")
    long countAllTransactions();

    @Query("SELECT p FROM Payment p ORDER BY p.transactionDate DESC")
    List<Payment> findLatestPayments(Pageable pageable);

    default List<Payment> findTop5ByOrderByDateDesc() {
        return findLatestPayments((Pageable) PageRequest.of(0, 5));
    }
}
