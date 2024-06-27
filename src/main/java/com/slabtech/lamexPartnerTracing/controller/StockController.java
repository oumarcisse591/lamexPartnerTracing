package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.*;
import com.slabtech.lamexPartnerTracing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class StockController {

    private StockService stockService;
    private PartnerService partnerService;
    private TransactionService transactionService;
    private MovementService movementService;
    private CountryService countryService;


    @Autowired
    public StockController(StockService theStockService, PartnerService thePartnerService, TransactionService theTransactionService, MovementService theMovementService, CountryService theCountryService){
        stockService = theStockService;
        partnerService = thePartnerService;
        transactionService = theTransactionService;
        movementService = theMovementService;
        countryService = theCountryService;
    }

    @GetMapping("/list-stock")
    public String listOfStock(Model theModel){
        List<Stock> stocks = stockService.findAllStock();
        theModel.addAttribute("stocks", stocks);
        return "stock/stock-list";
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

    @GetMapping("/modify-stockInfo")
    public String modifyStockInfo(@RequestParam("id") int theId, Model theModel){
        Stock theStock = stockService.findStockById(theId);
        theModel.addAttribute("theStock", theStock);
        return "stock/modify-stock-info";
    }

    @GetMapping("/info-stock")
    public String infoStock(@RequestParam("id") int theId, Model theModel){
        Stock theStock = stockService.findStockById(theId);
        List<Transaction> transactions = transactionService.findTransactionsByStockId(theId);
        theModel.addAttribute("theStock", theStock);
        theModel.addAttribute("transactions", transactions);
        theModel.addAttribute("stockBalance", stockService.calculateBalance(theId));
        return "stock/stock-info";
    }


    @PostMapping("/save-stock")
    public String saveStock(@ModelAttribute("stock") Stock theStock, RedirectAttributes redirectAttributes){

        LocalDate today = LocalDate.now();
        Date now = new Date();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));

        Random random = new Random();

        int randomNumber = random.nextInt(900000) + 100000;
        String code = "MV" + month + date + "-" + randomNumber;


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
