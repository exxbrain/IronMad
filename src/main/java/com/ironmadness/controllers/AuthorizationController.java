package com.ironmadness.controllers;

import com.ironmadness.service.VkService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@Controller
public class AuthorizationController {
    @Autowired
    private VkService vkService;

    @GetMapping("/login")
    public String authVkApi(Model model){
        model.addAttribute("code", vkService.appCode());
        return "login";
    }
    @GetMapping("/auth")
    public String code(@PathParam("code") String code) throws Exception {
        vkService.accessToken(code);
        return "redirect: home";
    }
}
