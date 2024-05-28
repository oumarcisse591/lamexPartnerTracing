package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.ClientDao;
import com.slabtech.lamexPartnerTracing.dao.ReceiptDao;
import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Receipt;
import com.slabtech.lamexPartnerTracing.service.PartnerService;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {

    ReceiptDao receiptDao;
    ClientDao clientDao;
    UserDao userDao;

    PaymentService paymentService;
    StockService stockService;
    PartnerService partnerService;

    @Autowired
    public AppController(ReceiptDao theReceiptDao, ClientDao theClientDao, UserDao theUserDao, PaymentService thePaymentService, StockService theStockService, PartnerService thePartnerService){
        receiptDao = theReceiptDao;
        clientDao = theClientDao;
        userDao = theUserDao;
        paymentService = thePaymentService;
        stockService = theStockService;
        partnerService = thePartnerService;
    }

    @GetMapping("/")
    public String showHome(Model theModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<Receipt> theReceipts = receiptDao.findLastsReceipts();
        List<Payment> payments = paymentService.getLatestPayments();
        Receipt theReceipt = new Receipt();
        Long receiptCount = receiptDao.getCount();
        Long clientCount = clientDao.getClientCount();
        Long userCount = userDao.getUserCount();
        long paymentCount = paymentService.getTotalTransactions();
        long stockCount = stockService.countAllStocks();
        long partnerCount = partnerService.countAllPartners();
        theModel.addAttribute("receiptCount", receiptCount);
        theModel.addAttribute("clientCount", clientCount);
        theModel.addAttribute("userCount", userCount);
        theModel.addAttribute("receipts", theReceipts);
        theModel.addAttribute("receipt", theReceipt);
        theModel.addAttribute("paymentCount", paymentCount);
        theModel.addAttribute("stockCount", stockCount);
        theModel.addAttribute("partnerCount", partnerCount);
        theModel.addAttribute("payments", payments);
        theModel.addAttribute("userName", userName);
        return "index";
    }
}
