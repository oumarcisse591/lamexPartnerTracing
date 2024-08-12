package com.slabtech.lamexPartnerTracing.controller;

import ch.qos.logback.core.model.conditional.ThenModel;
import com.slabtech.lamexPartnerTracing.dao.RoleDao;
import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.dto.PartnerUserDto;
import com.slabtech.lamexPartnerTracing.entity.Guichet;
import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Role;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.repository.UserRepository;
import com.slabtech.lamexPartnerTracing.service.GuichetService;
import com.slabtech.lamexPartnerTracing.service.PartnerService;
import com.slabtech.lamexPartnerTracing.service.UserService;
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
import java.util.*;

@Controller
public class PartnerController {

    private PartnerService partnerService;
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;
    private RoleDao roleDao;
    private GuichetService guichetService;

    @Autowired
    public PartnerController(PartnerService thePartnerService, UserDao theUserDao, RoleDao theRoleDao, GuichetService theGuichetService){
        partnerService = thePartnerService;
        userDao = theUserDao;
        roleDao = theRoleDao;
        guichetService = theGuichetService;
    }

    @GetMapping("/list-partner")
    public String listOfPartner(Model theModel){
        List<Partner> partners = partnerService.findAllPartner();
        theModel.addAttribute("partners", partners);
        return "partner/partner-list";
    }

    @GetMapping("/add-partner")
    public String addPartner(Model theModel){
        PartnerUserDto thePartner = new PartnerUserDto();
//        Partner thePartner = new Partner();
        theModel.addAttribute("thePartner", thePartner);
        return "partner/add-partner";
    }

    @GetMapping("/update-partner")
    public String updatePartner(@RequestParam("id") UUID theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-update";
    }

    @GetMapping("/deactivate-partner")
    public String deactivatePartnerPage(@RequestParam("id") UUID theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-deactivate";
    }

    @GetMapping("/reactivate-partner")
    public String reactivatePartnerPage(@RequestParam("id") UUID theId, Model theModel){
        Partner thePartner = partnerService.findPartnerById(theId);
        theModel.addAttribute("thePartner", thePartner);
        return "partner/partner-reactivate";
    }

    @PostMapping("/save-partner")
    public String savePartner(
            @ModelAttribute("partner") Partner thePartner,
            @ModelAttribute PartnerUserDto partnerUserDto,
            @ModelAttribute User user,
            RedirectAttributes redirectAttributes) {

        // Générer le code partenaire unique
        String code = generatePartnerCode();

        // Initialiser le partenaire avec les informations de PartnerUserDto
        thePartner.setPartnerName(partnerUserDto.getPartnerName());
        thePartner.setPartnerAddress(partnerUserDto.getPartnerAdress());
        thePartner.setPartnerEmail(partnerUserDto.getPartnerEmail());
        thePartner.setPartnerPhone("null");  // Remplacer "null" par une valeur appropriée si possible
        thePartner.setEnabled(true);
        thePartner.setPartnerCode(code);

        // Sauvegarder le partenaire
        Partner savedPartner = partnerService.savePartner(thePartner);

        // Initialiser et sauvegarder l'utilisateur avec vérification de l'existence du nom d'utilisateur
        if (userRepository.existsByUserName(partnerUserDto.getUserName())) {
            redirectAttributes.addFlashAttribute("error", "Le nom d'utilisateur existe déjà");
            return "redirect:/add-partner";
        }

        User savedUser = initializeAndSaveUser(user, partnerUserDto, savedPartner);

        // Initialiser et sauvegarder le guichet, puis l'assigner à l'utilisateur
        initializeAndAssignGuichet(partnerUserDto, savedUser, savedPartner);

        redirectAttributes.addFlashAttribute("message", "Le partenaire a été créé avec succès");
        return "redirect:/list-partner";
    }

    private String generatePartnerCode() {
        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));
        int randomNumber = new Random().nextInt(900000) + 100000;
        return "PT" + month + date + randomNumber;
    }

    private User initializeAndSaveUser(User user, PartnerUserDto partnerUserDto, Partner savedPartner) {
        user.setPartner(savedPartner);
        user.setName(partnerUserDto.getName());
        user.setUserName(partnerUserDto.getUserName());
        user.setPassword(partnerUserDto.getPassword());
        user.setRoles(partnerUserDto.getRole());
        return userDao.saveUser(user);  // Retourner l'utilisateur sauvegardé
    }

    private void initializeAndAssignGuichet(PartnerUserDto partnerUserDto, User savedUser, Partner savedPartner) {
        Guichet guichet = new Guichet();
        guichet.setCodeGuichet("GUI" + generateRandomNumber());
        guichet.setAdressGuichet(partnerUserDto.getAdresseGuichet());
        guichet.setPartner(savedPartner);

        // Sauvegarder le guichet avant de l'assigner à l'utilisateur
        Guichet savedGuichet = guichetService.saveGuichet(guichet);

        // Assigner le guichet sauvegardé à l'utilisateur et mettre à jour l'utilisateur
        savedUser.setGuichet(savedGuichet);
        userDao.saveUser(savedUser);
    }

    private int generateRandomNumber() {
        return new Random().nextInt(900000) + 100000;
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
