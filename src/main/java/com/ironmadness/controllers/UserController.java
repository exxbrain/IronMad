package com.ironmadness.controllers;

import com.ironmadness.domain.User;
import com.ironmadness.repos.User_Repo;
import com.ironmadness.service.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private User_Repo user_repo;

    @Autowired
    private User_Service user_service;

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@RequestParam String password,
                                @RequestParam String email,
                                @RequestParam("password2") String passwordDual,
                                @AuthenticationPrincipal User user,
                                Model model){

        boolean passwordDu = StringUtils.isEmpty(password);
        boolean passwordDu2 = StringUtils.isEmpty(passwordDual);

        if(!passwordDu || !passwordDu2){
            if(!passwordDual.equals(password)){
                model.addAttribute("passwordError",  "Пароли не совпадает" );
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
                return "profile";

            }
        }

        user_service.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

}
