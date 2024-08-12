package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    List<Payment> findAllPayment();

    List<Payment> findAllPaymentDesc();

    Page<Payment> findAllByPartner(Partner thePartner, PageRequest pageRequest);

    Payment findPaymentById(UUID theId);

    Payment savePayment(Payment thePayment);

    long getTotalTransactions();

    long getTotalPaymentsByPartner(Partner thePartner);

    List<Payment> getLatestPaymentsByPartner(Partner thePartner);

    List<Payment> getLatestPayments();

    Payment getLastPayment();
}
