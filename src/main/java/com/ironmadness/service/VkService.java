package com.ironmadness.service;

import com.ironmadness.config.WebSecurityConfig;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
/*
* Этот Сервис нужет для овтаризации через вк. Так как я решил делать по своему а не через OAuth2
* получилось не очень красиво но работает, еще бы сделать чтобы данная авторизация работала
* не только с вк но и другими api
*/


@Service
public class VkService {
    @Value("${app_id}")
    private Integer app_id;
    @Value("${REDIRECT_URI}")
    private String redirectUrl;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private User_Repo userRepository;

    @Autowired
    private MailSendere mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest httpSession;

    @Autowired
    private WebSecurityConfig ws;

    public String appCode(){
        String vkCode = String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=email&response_type=code",
                app_id, redirectUrl);
        return vkCode;
    }

    @SessionScope
    public void accessToken(String code, HttpServletRequest httpServletRequest) throws Exception {

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse =  vk.oauth()
                .userAuthorizationCodeFlow(app_id, clientSecret, redirectUrl, code)
                .execute();
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        //получаем данные с вк которые запишем в бд
        String tokenApi = actor.getAccessToken();
        //id пользователя vk
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


        //запишем некоторые параметры в сессию
        httpSession.getSession().setAttribute("id_vk", idApi);
        //мыло пользователя тоже запишем в параметр сессии
        httpSession.getSession().setAttribute("mail_api", authResponse.getEmail());

        //с помощью параметра в сессии будет показывать окно создание пользователя если он не создан
        if(apiRepository.findByUserId(idApi).getUser() == null){
            httpSession.getSession().setAttribute("apiname", "false");
        }else {
            Api api = apiRepository.findByUserId(idApi);

            //так как авторизация происходит с помощью созданого пользователя, нужено вставлять пароль который хеширован
            // я решил делать каждый раз при входе новый пароль но это както не очень, всетаки мне надо передать на почту
            // пользователя этот пароль, а это делать каждый раз при входе как то не правильно
            RandomPassword randomPassword = new RandomPassword();
            String newPass = randomPassword.generateRandomPassword(8);
            api.getUser().setPassword(passwordEncoder.encode(newPass));
            apiRepository.save(api);
            //аутафицируем пользователя
            Authentication authentication = new UsernamePasswordAuthenticationToken(api.getUser().getUsername(), newPass);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    public void addUserApi(String apiName) throws Exception {
        Api api = apiRepository.findByUserId((Integer) httpSession.getSession().getAttribute("id_vk"));
        String mail = (String) httpSession.getSession().getAttribute("mail_api");
        RandomPassword randomPassword = new RandomPassword();
        User user = new User();
        user.setUsername(apiName);
        user.setEmail(mail);
        user.setActive(true);
        String password = randomPassword.generateRandomPassword(8);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        api.setUser(user);
        apiRepository.save(api);
        httpSession.getSession().setAttribute("apiname", "true");
    }
}
