package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.*;
import com.slabtech.lamexPartnerTracing.repository.UserRepository;
import com.slabtech.lamexPartnerTracing.service.*;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
public class StockController {

    private StockService stockService;
    private PartnerService partnerService;
    private TransactionService transactionService;
    private MovementService movementService;
    private CountryService countryService;
    private UserRepository userRepository;


    @Autowired
    public StockController(StockService theStockService, PartnerService thePartnerService, TransactionService theTransactionService, MovementService theMovementService, CountryService theCountryService, UserRepository theUserRepository){
        stockService = theStockService;
        partnerService = thePartnerService;
        transactionService = theTransactionService;
        movementService = theMovementService;
        countryService = theCountryService;
        userRepository = theUserRepository;
    }

    @GetMapping("/list-stock")
    public String listOfStock(Model theModel){

        // Récupérer les informations d'authentification de l'utilisateur courant
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = currentUser.getPartner();

        // Déclarer la liste des stocks
        List<Stock> stocks;

        // Vérifier si le partenaire est null
        if (partner != null) {
            // Si le partenaire n'est pas null, récupérer les stocks associés au partenaire
            stocks = stockService.findAllByPartner(partner);
        } else {
            // Si le partenaire est null, récupérer tous les stocks
            stocks = stockService.findAllStock();
        }

        // Ajouter les stocks au modèle
        theModel.addAttribute("stocks", stocks);
        return "stock/stock-list";
    }

    @GetMapping("list-stock-partner")
    public String listStockPartners(@RequestParam("id") UUID theId, Model theModel){
        Partner partner = partnerService.findPartnerById(theId);
        List<Stock> stocks = stockService.findAllByPartner(partner);
        theModel.addAttribute("stocks", stocks);
        theModel.addAttribute("partner", partner);
        theModel.addAttribute("partnerName", partner.getPartnerName());
        return "stock/stock-list-partner";
    }

    @GetMapping("/add-stock")
    public String addStockPage(Model theModel){

        Stock stock = new Stock();
        List<Partner> partners = partnerService.findAllPartner();
        List<Country> countries = countryService.findAllCountry();
        theModel.addAttribute("stock", stock);
        theModel.addAttribute("partners", partners);
        theModel.addAttribute("countries", countries);
        return "stock/add-stock";
    }

    @GetMapping("/add-stock-partner")
    public String addStockPagePartner(@RequestParam("id") UUID theId, Model theModel){
        Stock stock = new Stock();
        List<Partner> partners = partnerService.findAllPartner();
        Partner partner = partnerService.findPartnerById(theId);
        String partnerName = partner.getPartnerName();
        List<Country> countries = countryService.findAllCountry();
        theModel.addAttribute("stock", stock);
        theModel.addAttribute("partner", partner);
        theModel.addAttribute("countries", countries);
        theModel.addAttribute("partnerName", partnerName);
        return "stock/add-stock-partner";
    }

    @GetMapping("/modify-stockInfo")
    public String modifyStockInfo(@RequestParam("id") UUID theId, Model theModel){
        Stock theStock = stockService.findStockById(theId);
        theModel.addAttribute("theStock", theStock);
        return "stock/modify-stock-info";
    }

    @GetMapping("/info-stock")
    public String infoStock(@RequestParam("id") UUID theId, Model theModel){
        Stock theStock = stockService.findStockById(theId);
        List<Transaction> transactions = transactionService.getLastsTenTransactions(theId);
        theModel.addAttribute("theStock", theStock);
        theModel.addAttribute("transactions", transactions);
        theModel.addAttribute("stockBalance", stockService.calculateBalance(theId));
        return "stock/stock-info";
    }


    @PostMapping("/save-stock")
    public String saveStock(@ModelAttribute("stock") Stock theStock, RedirectAttributes redirectAttributes){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = currentUser.getPartner();

        LocalDate today = LocalDate.now();
        Date now = new Date();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));

        Random random = new Random();

        int randomNumber = random.nextInt(900000) + 100000;
        String code = "MV" + month + date + "-" + randomNumber;


        theStock.setCreatedAt(now);
        theStock.setEnabled(true);
        theStock.setPartner(partner);
        stockService.saveStock(theStock);
        redirectAttributes.addFlashAttribute("message","Le stock a été créé");
        return "redirect:/list-stock";
    }

    @PostMapping("/save-stock-partner")
    public String saveStockPartner(@ModelAttribute("stock") Stock theStock, @RequestParam("id") UUID theId, RedirectAttributes redirectAttributes){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = partnerService.findPartnerById(theId);

        LocalDate today = LocalDate.now();
        Date now = new Date();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));

        theStock.setPartner(partner);
        theStock.setCreatedAt(now);
        theStock.setEnabled(true);
        stockService.saveStock(theStock);
        redirectAttributes.addFlashAttribute("message","Le stock a été créé");
        return "redirect:/list-stock";
    }

    @PostMapping("/save-modify-stock")
    public String saveModifyStock(@ModelAttribute("stock") Stock theStock, RedirectAttributes redirectAttributes){
        theStock.setEnabled(true);
        stockService.saveStock(theStock);
        redirectAttributes.addFlashAttribute("message","Le stock a été créé");
        return "redirect:/list-stock";
    }

}
