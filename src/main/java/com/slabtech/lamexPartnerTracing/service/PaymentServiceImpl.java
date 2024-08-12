package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.exception.NullAmountException;
import com.slabtech.lamexPartnerTracing.repository.PaymentRepository;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;

    private StockRepository stockRepository;

    private StockService stockService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository thePaymentRepository, StockRepository theStockRepository, StockService theStockService){

        paymentRepository = thePaymentRepository;
        stockRepository = theStockRepository;
        stockService = theStockService;
    }
    @Override
    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    /**
     * Renvoie tous les payements
     * @return
     */
    @Override
    public List<Payment> findAllPaymentDesc() {
        return paymentRepository.findAllByOrderByIdTransactionDesc();
    }

    /**
     * Renvoyer la liste de de payement lie au partenaire
     * @param thePartner
     * @return
     */
    @Override
    public Page<Payment> findAllByPartner(Partner thePartner, PageRequest pageRequest) {
        return paymentRepository.findByPartnerOrderByTransactionDateDesc(thePartner, pageRequest);
    }

    @Override
    public Payment findPaymentById(UUID theId) {
        return paymentRepository.findById(theId).orElse(null);
    }

    @Override
    public Payment savePayment(Payment thePayment) {
        if (thePayment.getTransactionAmount() == 0){
            throw new NullAmountException("Le paiement ne peut etre de 0.0");
        }
        else if (thePayment.getTransactionType().equals("debit")){
            stockService.decreaseStockQuantity(thePayment.getStock().getId(), thePayment.getTransactionAmount());
        }
        else if (thePayment.getTransactionType().equals("credit")){
            stockService.increaseStockQuantity(thePayment.getStock().getId(), thePayment.getTransactionAmount());
        }
        return paymentRepository.save(thePayment);
    }

    @Override
    public long getTotalTransactions() {
        return paymentRepository.countAllTransactions();
    }

    @Override
    public long getTotalPaymentsByPartner(Partner thePartner) {
        return paymentRepository.countPaymentsByPartner(thePartner);
    }

    @Override
    public List<Payment> getLatestPaymentsByPartner(Partner thePartner) {
        return paymentRepository.findTop5ByPartnerOrderByTransactionDateDesc(thePartner);
    }

    @Override
    public List<Payment> getLatestPayments() {
        return paymentRepository.findTop5ByOrderByDateDesc();
    }

    @Override
    public Payment getLastPayment() {
        return paymentRepository.findFirstByOrderByIdTransactionDesc();
    }
}
