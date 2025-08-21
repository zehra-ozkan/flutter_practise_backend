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




//    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//    }


    public String validateLogin(User user) {

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUser_password()));
        System.out.println("we come here?");
        //return authentication.isAuthenticated() ? "what is going on??" : "Noooooo login not successfull";
        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUserName()) : "Noooooo login not successfull";
    }
//    public List<User> getAll(){
//        return this.userRepo.findAll(); //select * from department
//    }
//
//    public User getById(int id){
//        return this.userRepo.findById(id).get(); //get protects form null
//    }
//
//    public User getByName(String id){
//        return this.userRepo.findByUserName(id).get(); //get protects form null
//    }
//
    public User addUser(User user){
      user.setUser_password(encoder.encode(user.getUser_password())); //hashes the password

        return this.userRepository.save(user);
    }
//
//    public User update(int id, User user){
//        Optional<User> optUser = this.userRepo.findById(id);
//        if(optUser.isPresent()){
//            optUser.get().setBirthday(user.getBirthday());
//            optUser.get().setUser_password(passwordEncoder.encode(user.getUser_password())); // hashes the password
//            optUser.get().setUserName(user.getUserName());
//            return this.userRepo.save(optUser.get());
//        }
//        throw new RuntimeException("User not found");
//    }
//    public void delete(int id){
//        this.userRepo.deleteById(id);
//    }
}
