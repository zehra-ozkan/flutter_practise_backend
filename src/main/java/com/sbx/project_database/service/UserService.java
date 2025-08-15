package com.sbx.project_database.service;

import com.sbx.project_database.persistence.User;
import com.sbx.project_database.persistence.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//this class handlese the business logic
@Service
public class UserService {
    private final UserRepository userRepository;
    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public boolean validateLogin(String username, String password) {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isEmpty()) return false;
        User r = user.get();
        return passwordEncoder.matches(password, r.getUser_password());
    }
    public List<User> getAll(){
        return this.userRepo.findAll(); //select * from department
    }

    public User getById(int id){
        return this.userRepo.findById(id).get(); //get protects form null
    }

    public User getByName(String id){
        return this.userRepo.findByUserName(id).get(); //get protects form null
    }

    public User add(User user){
        user.setUser_password(passwordEncoder.encode(user.getUser_password())); //hashes the password
        return this.userRepo.save(user);
    }

    public User update(int id, User user){
        Optional<User> optUser = this.userRepo.findById(id);
        if(optUser.isPresent()){
            optUser.get().setBirthday(user.getBirthday());
            optUser.get().setUser_password(passwordEncoder.encode(user.getUser_password())); // hashes the password
            optUser.get().setUserName(user.getUserName());
            return this.userRepo.save(optUser.get());
        }
        throw new RuntimeException("User not found");
    }
    public void delete(int id){
        this.userRepo.deleteById(id);
    }
}
