package com.ironmadness.controllers;

import com.ironmadness.service.VkService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class AuthorizationController {
    @Autowired
    private VkService vkService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @GetMapping("/login")
    public String authVkApi(Model model){
        model.addAttribute("code", vkService.appCode());
        return "login";
    }
    @GetMapping("/auth")
    public String code(@PathParam("code") String code) throws Exception {
        vkService.accessToken(code, httpServletRequest);
        return "redirect:home";
    }

    @RequestMapping(value = "/apiuser", method = RequestMethod.POST)
    public String apiName(@RequestParam("apiname")String apiName) throws Exception {
        vkService.addUserApi(apiName);
        return "redirect:home";
    }
}
