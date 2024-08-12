package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.ClientDao;
import com.slabtech.lamexPartnerTracing.entity.Client;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Random;

@Controller
public class ClientController {

    private ClientDao clientDao;

//    JavaMailSender javaMailSender;

//    Configuration configuration;

    @Autowired
    public ClientController(ClientDao theClientDao){
        clientDao = theClientDao;
    }

    @GetMapping("/list-client")
    public String getListClient(Model theModel){
        List<Client> theClients = clientDao.findAllClient();
        theModel.addAttribute("clients", theClients);
        return "client/liste-client";
    }

    @GetMapping("/add-client")
    public String addClient(Model theModel){
        Client theClient = new Client();
        theModel.addAttribute("client", theClient);
        return "client/ajout-client";
    }

    @PostMapping("/save-client")
    public String saveClient(@ModelAttribute("client") Client theClient, RedirectAttributes redirectAttributes) throws MessagingException {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));

//        Client lastClient = clientDao.findFirstByOrderByUdDesc();
//        int nextId = lastClient.getId() + 1;

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        theClient.setEnabled(true);
        theClient.setCodeClient("COCL"+ date + "-" + String.format("%04d", randomNumber));
//        sendSimpleMessage("e.checkpoints@gmail.com","sujet test","ceci est un test", "invoice");
        clientDao.saveClient(theClient);
        redirectAttributes.addFlashAttribute("message", "Le client a été ajouté avec succès");
        return "redirect:/list-client";
    }

    @PostMapping("/saveupdate-client")
    public String saveUpdateClient(@ModelAttribute("client") Client theClient, RedirectAttributes redirectAttributes){
        theClient.setEnabled(true);
        clientDao.saveClient(theClient);
        redirectAttributes.addFlashAttribute("message", "Le client a été modifié");
        return "redirect:/list-client";
    }

    @GetMapping("/update-client")
    public String updateClient(@RequestParam("id") int theId, Model theModel){
        Client theClient = clientDao.findClientById(theId);
        theModel.addAttribute("client", theClient);
        return "client/client-update";
    }
    @GetMapping("/deleting-client-page")
    public String deleteClient(@RequestParam("id") int theId, Model theModel){
        Client theClient = clientDao.findClientById(theId);
        theModel.addAttribute("client", theClient);
        return "client/client-delete";
    }

    @PostMapping("/delete-client")
    public String deleteClient(@ModelAttribute("client") Client theClient, RedirectAttributes redirectAttributes){
        theClient.setEnabled(false);
        clientDao.saveClient(theClient);
        redirectAttributes.addFlashAttribute("message", "Vous avez supprimer un client");
        return "redirect:/list-client";
    }
}
