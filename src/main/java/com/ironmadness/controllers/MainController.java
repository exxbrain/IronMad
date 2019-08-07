package com.ironmadness.controllers;

import com.ironmadness.repos.UserChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserChannel userChannel;

    @GetMapping("/")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Home") String name,
                       Model model){
        model.addAttribute("name", name);
        return "home";
    }
}
