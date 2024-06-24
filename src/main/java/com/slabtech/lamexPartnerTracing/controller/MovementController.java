package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.Movement;
import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.service.MovementService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class MovementController {

    private MovementService movementService;
    private StockService stockService;

    private UserService userService;

    @Autowired
    public MovementController(MovementService theMovementService, StockService theStockService, UserService theUserService){
        movementService = theMovementService;
        stockService = theStockService;
        userService = theUserService;
    }

    @GetMapping("/list-movement")
    public String movementList(Model theModel){
        List<Movement> movements = movementService.findAllMovement();
        theModel.addAttribute("movements", movements);
        return "movement/movement-list";
    }

    @GetMapping("/add-movement")
    public String addMovementPage(@RequestParam("id") int theId, Model theModel){
        Movement movement = new Movement();
        Stock theStock = stockService.findStockById(theId);
        movement.setStock(theStock);
        theModel.addAttribute("theStock", theStock);
        theModel.addAttribute("movement", movement);
        return "stock/mouvement-stock";
    }

    @GetMapping("/add-recharge")
    public String addRecharge(@RequestParam("id") int theId, Model theModel){
        Movement movement = new Movement();
        Stock theStock = stockService.findStockById(theId);
        movement.setStock(theStock);
        theModel.addAttribute("theStock", theStock);
        theModel.addAttribute("movement", movement);
        return "stock/recharge-stock";
    }

    @GetMapping("/recharge-success")
    public String recharge(@RequestParam("id") int theId, Model theModel){
        Movement movement = movementService.findMovementById(theId);
        theModel.addAttribute("movement", movement);
        return "stock/recharge-success";
    }

    @PostMapping("/save-movement")
    public String saveMovement(@ModelAttribute("movement")Movement theMovement, RedirectAttributes redirectAttributes){

        try{
            LocalDate today = LocalDate.now();
            Date now = new Date();
            String date = today.format(DateTimeFormatter.ofPattern("yy"));
            String month = today.format(DateTimeFormatter.ofPattern("MM"));

            Random random = new Random();

            int randomNumber = random.nextInt(900000) + 100000;
            String code = "MV" + month + date + "-" + randomNumber;

            theMovement.setReferenceTransaction(code);
            theMovement.setTransactionDate(now);
            Movement registeredMovement = movementService.saveMovement(theMovement);
            int id = registeredMovement.getIdTransaction();
            int idStock = registeredMovement.getStock().getId();
            stockService.calculateBalance(idStock);
            redirectAttributes.addFlashAttribute("message","Vous venez de faire un mouvement sur le stock");
            return "redirect:/list-stock";
        }
        catch (InsufficientStockException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/list-stock";
        }
    }

    @PostMapping("/save-recharge")
    public String saveRecharge(@ModelAttribute("movement")Movement theMovement, RedirectAttributes redirectAttributes){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            LocalDate today = LocalDate.now();
            Date now = new Date();
            String date = today.format(DateTimeFormatter.ofPattern("yy"));
            String month = today.format(DateTimeFormatter.ofPattern("MM"));

            Random random = new Random();

            int randomNumber = random.nextInt(900000) + 100000;
            String code = "RC" + month + date + "-" + randomNumber;

            theMovement.setReferenceTransaction(code);
            theMovement.setTransactionDate(now);
            theMovement.setReason("Recharge du stock partenaire");
            theMovement.setTransactionType("recharge");
            User user = userService.findByUserName(userName);
            theMovement.setUser(user);
            Movement registeredMovement = movementService.saveMovement(theMovement);
            int id = registeredMovement.getIdTransaction();
            int idStock = registeredMovement.getStock().getId();
            stockService.calculateBalance(idStock);
            redirectAttributes.addFlashAttribute("message","Vous venez de faire un mouvement sur le stock");
            return "redirect:/recharge-success?id="+ id;
        }
        catch (InsufficientStockException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/list-stock";
        }
    }
}
