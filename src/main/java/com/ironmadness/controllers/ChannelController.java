package com.ironmadness.controllers;

import com.ironmadness.repos.UserChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final UserChannel userChannel;

    @Autowired
    public ChannelController(UserChannel userChannel) {
        this.userChannel = userChannel;
    }

    @GetMapping
    public String channel (Model model){
        model.addAttribute("channels", userChannel.findAll());

        return "channel";
    }
}
