package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAllPayment();

    List<Payment> findAllPaymentDesc();

    Payment findPaymentById(int theId);

    Payment savePayment(Payment thePayment);

    long getTotalTransactions();

    List<Payment> getLatestPayments();
}
