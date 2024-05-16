package com.slabtech.lamexPartnerTracing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/authentification")
    public String authentification(){
        return "login-page";
    }
}
