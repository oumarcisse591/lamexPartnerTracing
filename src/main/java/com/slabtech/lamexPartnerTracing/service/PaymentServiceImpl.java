package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.exception.NullAmountException;
import com.slabtech.lamexPartnerTracing.repository.PaymentRepository;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Payment> findAllPaymentDesc() {
        return paymentRepository.findAllByOrderByIdTransactionDesc();
    }

    @Override
    public Payment findPaymentById(int theId) {
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
}
