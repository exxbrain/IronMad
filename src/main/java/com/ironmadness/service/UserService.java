package com.ironmadness.service;

import com.ironmadness.domain.Channel;
import com.ironmadness.domain.Role;
import com.ironmadness.domain.User;
import com.ironmadness.repos.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private User_Repo userRepo;

    @Autowired
    private MailSendere mailSender;

    @Value("${active.mail}")
    private String activeMail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    public boolean addUser(User user) {
        User userFormDB = userRepo.findByUsername(user.getUsername());

        if(userFormDB != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivate(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Здравствуйте, %s \n" +
                            "пожалуйста пройдите по ссылке для авторизации: " +
                            activeMail + "/activate/%s",
                    user.getUsername(),
                    user.getActivate()
            );
            mailSender.send(user.getEmail(), "Активация кода", message);
        }

        return true;
    }

    public void updateProfile(User user, String password, String email){
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.equals(email));

        if(isEmailChanged){
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)){
                user.setActivate(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepo.save(user);
    }
    public void updateChannel(Channel channel, String name, String text, String avatar){

    }

    public String addFile(MultipartFile file, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFile = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFile));
        return resultFile;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivate(code);

        if(user == null){
            return false;
        }

        user.setActivate(null);

        userRepo.save(user);

        return true;
    }
}
