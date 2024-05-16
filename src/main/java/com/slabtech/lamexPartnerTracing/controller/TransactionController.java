package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.Transaction;
import com.slabtech.lamexPartnerTracing.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService theTransactionService){
        transactionService = theTransactionService;
    }

    @GetMapping("/transaction-list")
    public String transactionList(Model theModel){
        List<Transaction> transactions = transactionService.findAllTransaction();
        theModel.addAttribute("transactions", transactions);
        return "transaction/transaction-list";
    }

}
