package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.SocietyDao;
import com.slabtech.lamexPartnerTracing.entity.Society;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SocietyController {

    private SocietyDao societyDao;

    @Autowired
    public SocietyController(SocietyDao theSocietyDao){
        societyDao = theSocietyDao;
    }

    @GetMapping("/society")
    public String getSociety(Model theModel){
        List<Society> theSocieties = societyDao.findAllSociety();
        theModel.addAttribute("societies", theSocieties);
        return "societe/list-societe";
    }

    @PostMapping("save-society")
    public String saveSociety(@ModelAttribute("society") Society theSociety){
        societyDao.saveSociety(theSociety);
        return "redirect:/society";
    }

    @GetMapping("update-society")
    public String updateSociety(@RequestParam("id") int theId, Model theModel){
        Society theSociety = societyDao.findSocietyById(theId);
        theModel.addAttribute("society", theSociety);
        return "societe/update-society";
    }
}
