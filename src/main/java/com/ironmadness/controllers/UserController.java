package com.ironmadness.controllers;

import com.ironmadness.domain.Channel;
import com.ironmadness.domain.Role;
import com.ironmadness.domain.User;
import com.ironmadness.repos.UserChannel;
import com.ironmadness.repos.User_Repo;
import com.ironmadness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserChannel userChannel;

    @Autowired
    private User_Repo userRepo;

    @Autowired
    private UserService user_service;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@RequestParam String password,
                                @RequestParam String email,
                                @RequestParam("password2") String passwordDual,
                                @AuthenticationPrincipal User user,
                                Model model) {

        boolean passwordDu = StringUtils.isEmpty(password);
        boolean passwordDu2 = StringUtils.isEmpty(passwordDual);

        if (!passwordDu || !passwordDu2) {
            if (!passwordDual.equals(password)) {
                model.addAttribute("passwordError", "Пароли не совпадает");
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
                return "profile";

            }
        }

        user_service.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER_CHANEL')")
    @GetMapping("/channeledit")
    public String channelUser(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("channel", user.getChannelUser());

        return "channeledit";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER_CHANEL')")
    @PostMapping("channeledit")
    public String channeledit(
            @AuthenticationPrincipal User user,
            @Valid Channel channel,
            BindingResult bindingResult,
            Model model,
            @RequestParam("filePath") MultipartFile file) throws IOException {

        //проверка на пустату форм, если форма пустая то выдает ошибку
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ErrorsController.getStringStringMap(bindingResult);

            model.mergeAttributes(errors);

            return "channeledit";
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String resultFile = user_service.addFile(file, uploadPath);
                channel.setAvatar(resultFile);
            }else{
                channel.setAvatar(user.getChannelUser().getAvatar());
            }
            //если канал у пользователя не пустой, то обнавляем канал по id канала, находящийся у пользователя
            //если канал у пользователя пустой, то зодаем новый канал и добавляем пользователю
            if(user.getChannelUser() != null ){
                channel.setId(user.getChannelUser().getId());
            }
            user.setChannelUser(channel);
        }
                userChannel.save(channel);
                userRepo.save(user);
        return "redirect:/user/channeledit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user){

        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);

        return "redirect:/user";
    }

}
