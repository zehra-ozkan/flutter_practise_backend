package com.sbx.project_database.service;

import com.sbx.project_database.models.User;
//import com.sbx.project_database.models.UserRepository;
import com.sbx.project_database.models.UserPrincipal;
import com.sbx.project_database.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//this class handlese the business logic
@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    private final int STRENGHT = 10;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(STRENGHT);

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JWTService jwtService;

    public String validateLogin(User user) {

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUser_password()));

        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUserName()) : "failed";
    }

    public User addUser(User user){

        user.setUser_password(encoder.encode(user.getUser_password())); //hashes the password
        return this.userRepository.save(user);

    }

    public User getUserByToken(String token) {
        System.out.println("Inside the getUserby token");
        String userName = jwtService.extractUserName(token);
        System.out.println("Userneme is " + userName);
        return this.userRepository.findByUserName(userName).get();
    }

}
