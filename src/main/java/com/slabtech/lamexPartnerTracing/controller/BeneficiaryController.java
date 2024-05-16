package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.BeneficiaryDao;
import com.slabtech.lamexPartnerTracing.entity.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BeneficiaryController {

    private BeneficiaryDao beneficiaryDao;

    @Autowired
    public BeneficiaryController(BeneficiaryDao theBeneficiaryDao){
        beneficiaryDao = theBeneficiaryDao;
    }

    @GetMapping("/list-beneficiary")
    public String getBeneficiaryList(Model theModel){
        List<Beneficiary> beneficiaries = beneficiaryDao.findAllBeneficiary();
        theModel.addAttribute("beneficiaries", beneficiaries);
        return "beneficiaire/liste-beneficiaire";
    }

    @GetMapping("/add-beneficiary")
    public String addBeneficiary(Model theModel){
        Beneficiary theBeneficiary = new Beneficiary();
        theModel.addAttribute("beneficiary", theBeneficiary);
        return "beneficiaire/ajout-beneficiaire";
    }

    @GetMapping("/update-beneficiary")
    public String updateBeneficiary(@RequestParam("id") int theId, Model theModel){
        Beneficiary theBeneficiary = beneficiaryDao.findBeneficiaryById(theId);
        theModel.addAttribute("beneficiary", theBeneficiary);
        return "beneficiaire/beneficiary-update";
    }

    @GetMapping("/delete-beneficiary-page")
    public String deleteBeneficiaryPage(@RequestParam("id") int theId, Model theModel){
        Beneficiary theBeneficiary = beneficiaryDao.findBeneficiaryById(theId);
        theModel.addAttribute("beneficiary", theBeneficiary);
        return "beneficiaire/beneficiary-delete";
    }

    @PostMapping("/delete-beneficiary")
    public String deleteBeneficiary(@ModelAttribute("beneficiary") Beneficiary theBeneficiary, RedirectAttributes redirectAttributes){
        theBeneficiary.setEnabled(false);
        beneficiaryDao.saveBeneficiary(theBeneficiary);
        redirectAttributes.addFlashAttribute("message", "Partenaire supprimé avec succès");
        return "redirect:/list-beneficiary";
    }

    @PostMapping("/save-beneficiary")
    public String saveBeneficiary(@ModelAttribute("beneficiary") Beneficiary theBeneficiary, RedirectAttributes redirectAttributes){
        theBeneficiary.setEnabled(true);
        beneficiaryDao.saveBeneficiary(theBeneficiary);
        redirectAttributes.addFlashAttribute("message", "Le bénéficiaire a été ajouté avec succès");
        return "redirect:/list-beneficiary";
    }
}
