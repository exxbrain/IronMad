package com.ironmadness.controllers;

import com.ironmadness.domain.User;
import com.ironmadness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService user_service;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password2")String passwordDual,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model){

        boolean isConfigEmpty = StringUtils.isEmpty(passwordDual);

        if(isConfigEmpty){
            model.addAttribute("password2Error", "Пароли не совпадают");
        }
        if(user.getPassword() != null && !user.getPassword().equals(passwordDual)){
            model.addAttribute("passwordError", "Пароли не равны");
        }
        if(isConfigEmpty || bindingResult.hasErrors()){
            Map<String, String> errors = ErrorsController.getStringStringMap(bindingResult);

            model.mergeAttributes(errors);
           return "/registration";
        }
        if(!user_service.addUser(user)){
            model.addAttribute("usernameError", "Такой юзер уже существует");
            return "/registration";
        }
        return "redirect:/login";
    }


}
