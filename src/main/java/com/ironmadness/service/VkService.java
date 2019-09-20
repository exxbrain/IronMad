package com.ironmadness.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class VkService {
    @Value("${app_id}")
    private String app_id;
    @Value("${REDIRECT_URI}")
    private String redirectUrl;

    public String appCode(){
        String vkCode = String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=emails&response_type=code",
                app_id, redirectUrl);
        return vkCode;
    }
}
