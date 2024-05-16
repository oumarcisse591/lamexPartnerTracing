package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.exception.NullAmountException;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import com.slabtech.lamexPartnerTracing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class PaymentController {

    private PaymentService paymentService;

    private StockService stockService;

    private UserService userService;

    @Autowired
    public PaymentController(PaymentService thePaymentService, StockService theStockService, UserService theUserService){
        paymentService = thePaymentService;
        stockService = theStockService;
        userService =theUserService;
    }

    @GetMapping("/payment-list")
    public String listPayment(Model theModel){
        List<Payment> payments = paymentService.findAllPaymentDesc();
        theModel.addAttribute("payments", payments);
        return "payment/list-payment";
    }

    @GetMapping("/add-payment")
    public String addPayment(Model theModel){
        Payment payment = new Payment();
        List<Stock> stocks = stockService.findAllStock();
        theModel.addAttribute("payment", payment);
        theModel.addAttribute("stocks", stocks);
        return "payment/add-payment";
    }

    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute("payment") Payment thePayment, RedirectAttributes redirectAttributes) throws MessagingException {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            LocalDate today = LocalDate.now();
            Date now = new Date();
            String date = today.format(DateTimeFormatter.ofPattern("yy"));
            String month = today.format(DateTimeFormatter.ofPattern("MM"));
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            String code = "PAY" + month + date + "-" + randomNumber;

            thePayment.setReason("Paiement Client");
            thePayment.setReferenceTransaction(code);
            thePayment.setTransactionDate(now);
            thePayment.setTransactionType("debit");
            User user = userService.findByUserName(userName);
            thePayment.setUser(user);
            paymentService.savePayment(thePayment);
            redirectAttributes.addFlashAttribute("message","Le paiement a ete effectue avec succes");
            return "redirect:/payment-list";
        } catch (InsufficientStockException | NullAmountException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/add-payment";
        }
    }
}
