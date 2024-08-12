package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.ClientDao;
import com.slabtech.lamexPartnerTracing.dao.ReceiptDao;
import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.entity.*;
import com.slabtech.lamexPartnerTracing.repository.UserRepository;
import com.slabtech.lamexPartnerTracing.service.PartnerService;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import com.slabtech.lamexPartnerTracing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;

@Controller
@ControllerAdvice
public class AppController {

    ReceiptDao receiptDao;
    ClientDao clientDao;
    UserDao userDao;
    UserRepository userRepository;

    PaymentService paymentService;
    StockService stockService;
    PartnerService partnerService;

    @Autowired
    UserService userService;

    @Autowired
    public AppController(ReceiptDao theReceiptDao, ClientDao theClientDao, UserDao theUserDao, PaymentService thePaymentService, StockService theStockService, PartnerService thePartnerService, UserRepository theUserRepository){
        receiptDao = theReceiptDao;
        clientDao = theClientDao;
        userDao = theUserDao;
        paymentService = thePaymentService;
        stockService = theStockService;
        partnerService = thePartnerService;
        userRepository = theUserRepository;
    }

    @ModelAttribute("globalVariable")
    public String entityName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String userName = authentication.getName();

            // Récupérer l'utilisateur courant à partir du nom d'utilisateur
            User currentUser = userRepository.findByUserName(userName);

            if (currentUser != null) {
                Partner partner = currentUser.getPartner();
                if (partner != null) {
                    // Retourner le nom du partenaire associé
                    return partner.getPartnerName();
                } else {
                    // Gérer le cas où l'utilisateur n'a pas de partenaire associé
                    return "Aucun partenaire associé";
                }
            }
        }

        // Retourner une valeur par défaut ou un message d'accueil lorsque l'utilisateur n'est pas authentifié
        return "Utilisateur non authentifié";
    }


    @ModelAttribute("guichet")
    public String guichetCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String userName = authentication.getName();

            // Récupérer l'utilisateur courant à partir du nom d'utilisateur
            User currentUser = userRepository.findByUserName(userName);

            if (currentUser != null) {
                Guichet guichet = currentUser.getGuichet();
                if (guichet != null) {
                    // Retourner le code du guichet associé
                    return guichet.getCodeGuichet();
                } else {
                    // Gérer le cas où l'utilisateur n'a pas de guichet associé
                    return "Pas de guichet associé";
                }
            }
        }

        // Retourner une valeur par défaut ou un message d'accueil lorsque l'utilisateur n'est pas authentifié
        return "Utilisateur non authentifié";
    }


    @ModelAttribute("GlobalStocks")
    public List<Stock> soldeStock() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String userName = authentication.getName();

            // Récupérer l'utilisateur courant à partir du nom d'utilisateur
            User currentUser = userRepository.findByUserName(userName);

            if (currentUser != null) {
                Partner partner = currentUser.getPartner();
                if (partner != null) {
                    // Récupérer les stocks associés au partenaire
                    return partner.getStocks();
                } else {
                    // Gérer le cas où l'utilisateur n'a pas de partenaire associé
                    return Collections.emptyList(); // Retourner une liste vide ou gérer comme vous le souhaitez
                }
            }
        }

        // Retourner une valeur par défaut ou une liste vide si l'utilisateur n'est pas authentifié
        return Collections.emptyList();
    }



    @GetMapping("/")
    public String showHome(Model theModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userRepository.findByUserName(userName);

        Partner thePartner = user.getPartner();

        List<Receipt> theReceipts = receiptDao.findLastsReceipts();
        List<Payment> payments = paymentService.getLatestPayments();
        Receipt theReceipt = new Receipt();
        Long receiptCount = receiptDao.getCount();
        Long clientCount = clientDao.getClientCount();
        Long userCount = userDao.getUserCount();
        long paymentCount = paymentService.getTotalTransactions();
        long stockCount = stockService.countAllStocks();
        long partnerCount = partnerService.countAllPartners();
        Payment theLastPayment = paymentService.getLastPayment();
        long inactiveStocksCount = stockService.countAllDisabledStocks();
        long inactivePartnersCount = partnerService.countInactivePartners();
        List<Stock> stocks = user.getPartner().getStocks();
        long userCountPartner = userService.countUserbyPartner(thePartner);
        long stockCountPartner = stockService.countStockBypartner(thePartner);
        List<Payment> latestPaymentsPartner = paymentService.getLatestPaymentsByPartner(thePartner);
        Long paymentCountPartner = paymentService.getTotalPaymentsByPartner(thePartner);
        theModel.addAttribute("receiptCount", receiptCount);
        theModel.addAttribute("clientCount", clientCount);
        theModel.addAttribute("userCount", userCount);
        theModel.addAttribute("receipts", theReceipts);
        theModel.addAttribute("receipt", theReceipt);
        theModel.addAttribute("paymentCount", paymentCount);
        theModel.addAttribute("stockCount", stockCount);
        theModel.addAttribute("partnerCount", partnerCount);
        theModel.addAttribute("payments", payments);
        theModel.addAttribute("userName", user.getName());
        theModel.addAttribute("lastPayment", theLastPayment);
        theModel.addAttribute("inactiveStocks", inactiveStocksCount);
        theModel.addAttribute("inactivePartners", inactivePartnersCount);
        theModel.addAttribute("roles", user.getRoles());
        theModel.addAttribute("stocks", stocks);
        theModel.addAttribute("paymentCountPartner", paymentCountPartner);
        theModel.addAttribute("userCountPartner", userCountPartner);
        theModel.addAttribute("stockCountPartner", stockCountPartner);
        theModel.addAttribute("latestPaymentsPartner", latestPaymentsPartner);
        return "index";
    }
}
