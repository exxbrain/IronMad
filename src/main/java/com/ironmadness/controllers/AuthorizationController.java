package com.ironmadness.controllers;

import com.ironmadness.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorizationController {
    @Autowired
    private VkService vkService;

    @GetMapping("/login")
    public String authVkApi(Model model){

        model.addAttribute("vkcode", vkService.appCode());
        return "login";
    }

}
