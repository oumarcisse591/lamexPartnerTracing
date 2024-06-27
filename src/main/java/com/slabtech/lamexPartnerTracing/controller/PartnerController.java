package com.slabtech.lamexPartnerTracing.controller;

import ch.qos.logback.core.model.conditional.ThenModel;
import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Part;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class PartnerController {

    private PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService thePartnerService){
        partnerService = thePartnerService;
    }

    @GetMapping("/list-partner")
    public String listOfPartner(Model theModel){
        List<Partner> partners = partnerService.findAllPartner();
        theModel.addAttribute("partners", partners);
        return "partner/partner-list";
    }

    @GetMapping("/add-partner")
    public String addPartner(Model theModel){
        Partner thePartner = new Partner();
        theModel.addAttribute("thePartner", thePartner);
        return "partner/add-partner";
    }

    @GetMapping("/update-partner")
    public String updatePartner(@RequestParam("id") int theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-update";
    }

    @GetMapping("/deactivate-partner")
    public String deactivatePartnerPage(@RequestParam("id") int theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-deactivate";
    }

    @GetMapping("/reactivate-partner")
    public String reactivatePartnerPage(@RequestParam("id") int theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-reactivate";
    }

    @PostMapping("/save-partner")
    public String savePartner(@ModelAttribute("partner")Partner thePartner, RedirectAttributes redirectAttributes){
        LocalDate today = LocalDate.now();
        Date now = new Date();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        String code = "PT" + month + date + randomNumber;
        thePartner.setEnabled(true);
        thePartner.setPartnerCode(code);
        partnerService.savePartner(thePartner);
        redirectAttributes.addFlashAttribute("message","Le partenaire a été créé avec succès");
        return "redirect:/list-partner";
    }

    @PostMapping("/saveUpdate-partner")
    public String saveUpdatePartner(@ModelAttribute("partner")Partner thePartner, RedirectAttributes redirectAttributes){
        thePartner.setEnabled(true);
        partnerService.savePartner(thePartner);
        redirectAttributes.addFlashAttribute("message","Le partenaire a été modifié avec succès");
        return "redirect:/list-partner";
    }

    @PostMapping("/delete-partner")
    public String deletePartner(@ModelAttribute("id") Partner thePartner, RedirectAttributes redirectAttributes){
        thePartner.setEnabled(false);
        partnerService.savePartner(thePartner);
        redirectAttributes.addFlashAttribute("message", "Le partenaire a été désactivé");
        return "redirect:/list-partner";
    }

    @PostMapping("/activate-partner")
    public String reactivatePartner(@ModelAttribute("id") Partner thePartner, RedirectAttributes redirectAttributes){
        thePartner.setEnabled(true);
        partnerService.savePartner(thePartner);
        redirectAttributes.addFlashAttribute("message", "Le partenaire a été reactive");
        return "redirect:/list-partner";
    }
}
