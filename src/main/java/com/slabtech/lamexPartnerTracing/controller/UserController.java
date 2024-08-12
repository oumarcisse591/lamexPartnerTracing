package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.entity.User;
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
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao theUserdao){ userDao = theUserdao;}

    @GetMapping("/list-user")
    public String listUser(Model theModel){
        List<User> theUsers = userDao.findAllUser();
        theModel.addAttribute("users", theUsers);
        return "user/list-user";
    }

    @GetMapping("/list-user-partner")
    public String listUserPartner(Model theModel){
        return "";
    }

    @GetMapping("/add-user")
    public String addUser(Model theModel){
        User theUser = new User();
        theModel.addAttribute("user", theUser);
        return "user/create-user";
    }

    @PostMapping("/create-user")
    public String saveUser(@ModelAttribute("user") User theUser, RedirectAttributes redirectAttributes) {
        // Vérifier si le nom d'utilisateur existe déjà
        User existingUser = userDao.findByUserName(theUser.getUserName());

        if (existingUser != null) {
            // Si le nom d'utilisateur existe, ajouter un message d'erreur
            redirectAttributes.addFlashAttribute("errorMessage", "Le nom d'utilisateur existe déjà. Veuillez en choisir un autre.");
            return "redirect:/add-user"; // Rediriger vers la page de création d'utilisateur
        }

        // Si le nom d'utilisateur n'existe pas, enregistrer l'utilisateur
        userDao.saveUser(theUser);
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès.");
        return "redirect:/list-user";
    }

    @PostMapping("/save-user")
    public String saveUpdatedUser(@ModelAttribute("user") User theUser, RedirectAttributes redirectAttributes) {
        userDao.saveUser(theUser);
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès.");
        return "redirect:/list-user";
    }


    @GetMapping("/update-user")
    public String updateUser(@RequestParam("id") int theId, Model theModel){
        User theUser = userDao.findUserById(theId);
        theModel.addAttribute("user", theUser);
        return "user/user-update";
    }

    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam("id") int theId){
        userDao.deleteUserById(theId);
        return "redirect:/list-user";
    }
}
