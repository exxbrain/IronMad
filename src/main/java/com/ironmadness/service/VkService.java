package com.ironmadness.service;

import com.ironmadness.domain.Role;
import com.ironmadness.domain.User;
import com.ironmadness.domain.Api;
import com.ironmadness.model.RandomPassword;
import com.ironmadness.repos.ApiRepository;
import com.ironmadness.repos.User_Repo;
import com.ironmadness.servlet.ApiServlet;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;


import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VkService {
    @Value("${app_id}")
    private Integer app_id;
    @Value("${REDIRECT_URI}")
    private String redirectUrl;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private String pass;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private User_Repo userRepository;

    @Autowired
    private MailSendere mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String appCode(){
        String vkCode = String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=email&response_type=code",
                app_id, redirectUrl);
        return vkCode;
    }
    public void accessToken(String code) throws Exception {

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse =  vk.oauth()
                .userAuthorizationCodeFlow(app_id, clientSecret, redirectUrl, code)
                .execute();
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        //получаем данные с вк которые запишем в бд
        String tokenApi = actor.getAccessToken();
        Integer idApi = actor.getId();

        List<UserXtrCounters> as = vk.users().get(actor).execute();
        UserXtrCounters userv = as.get(0);

        if(apiRepository.findByUserId(idApi) == null){
            Api api = new Api(idApi, tokenApi, "vk");
            apiRepository.save(api);
        }else {
            Api api = apiRepository.findByUserId(idApi);
            api.setAccessToken(tokenApi);
            apiRepository.save(api);
        }

        String userVk = "true";

        if(apiRepository.findByUserId(idApi).getUser() == null){
            userVk = "false";
        }else{
            userVk = "true";
        }

        ApiServlet apiServlet = new ApiServlet(userVk);
    }
}
