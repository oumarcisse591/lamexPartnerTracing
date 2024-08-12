package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByOrderByIdTransactionDesc();

    @Query("SELECT COUNT(p) FROM Payment p")
    long countAllTransactions();

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.partner = :partner")
    long countPaymentsByPartner(@Param("partner") Partner partner);

    @Query("SELECT p FROM Payment p ORDER BY p.transactionDate DESC")
    List<Payment> findLatestPayments(Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.partner = :partner ORDER BY p.transactionDate DESC")
    List<Payment> findTop5ByPartnerOrderByTransactionDateDesc(@Param("partner") Partner partner);

    default List<Payment> findTop5ByOrderByDateDesc() {
        return findLatestPayments((Pageable) PageRequest.of(0, 5));
    }

    Payment findFirstByOrderByIdTransactionDesc();


    /**
     * Recuperer les paiements d'un partenaire
     * Cela affichera uniquement les payment lie au partenaire
     * */
//    List<Payment> findByPartnerOrderByIdTransactionDesc(Partner partner);

    Page<Payment> findByPartnerOrderByTransactionDateDesc(Partner partner, Pageable pageable);
}
