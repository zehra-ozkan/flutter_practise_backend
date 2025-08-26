package com.sbx.project_database.controller;

import com.sbx.project_database.models.User;
import com.sbx.project_database.service.UserProfileService;
import com.sbx.project_database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Base64;



@RequestMapping("/app_users")
@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private AuthenticationManager authManager;

    private List<User> kk = new ArrayList<User>();

    @GetMapping("/students")
    public List<User> getUsers(){
        kk.add(new User("sadf"));
        kk.add(new User("saasdfdf"));
        kk.add(new User("sadddddddf"));

        return kk;
    }

    //this works
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user){

        System.out.println("recieved login request from " + user.getUserName() + user.getUser_password());
        String result = userService.validateLogin(user); //todo maybe I can send token= null in case of failure
        System.out.println("login result is = "+ result);

        if(result.equals("failed")){
            return ResponseEntity.status(401)
                    .body(Map.of("success", false));
        }
        return ResponseEntity.ok(Map.of(
                "success", true, //this is the boolean key
                "token", result // Sending user ID to Flutter
        ));
    }



    @PostMapping(value="/register")
    public ResponseEntity<Map<String, Object>> register (@RequestBody User user) {
        System.out.println("Requesting register for user:" + user.getUserName());
        System.out.println("Requesting register for user with password :" + user.getUser_password());
        System.out.println("Requesting register for user with birthday :" + user.getBirthday());
        try {
           User h =  userService.addUser(user);
            System.out.println("User added successfully");
            return ResponseEntity.ok(Map.of(
                    "success", true, //this is the boolean key
                    "message", "yippie" // Sending user ID to Flutter
            ));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of("message", "User already exists", "success", false));
        }

    }
    @GetMapping(value="/home")
    public ResponseEntity<Map<String, Object>> getHomeProfile(@RequestHeader("Authorization") String token) {

        try{
            System.out.println("\nrequesting home page with token **" + token + "**");
            System.out.println("Tokens contains whiteSpace : " + token.contains(" "));
            //token = token.replace(" ", "");
            User user = userService.getUserByToken(token.substring(7)); //"Bearer plus one whitespace todo do better checking of substring here
            byte[] profile = userProfileService.getImageByUserId(user.getUser_id()); //todo this must return null in the nonextistent case

            String base64Image = "";
            if(profile != null) {
                 base64Image = Base64.getEncoder().encodeToString(profile);
            }else {
                System.out.println("profile is null");
            }

            System.out.println("Accepted token with username " + user.getUserName());
            return ResponseEntity.ok()
                    .body(Map.of(
                            "userName", user.getUserName(),
                            "birthday", user.getBirthday(),
                            "picture", base64Image,
                            "success", true));

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of(
                            "message", e.getMessage(),
                            "success", false));

        }

    }

//    @GetMapping()
//    public List<User> getAll(){
//        return this.userService.getAll();
//    }
//
//    @GetMapping(value="/id/{id}")
//    public User getById(@PathVariable int id){
//        return this.userService.getById(id);
//    }
//    @GetMapping(value="/name/{name}")
//    public User getByName(@PathVariable String name){
//        return this.userService.getByName(name);
//    }
//
//
//    @PutMapping(value="/{id}" , consumes = "application/json")
//    public User update(@PathVariable int id,@RequestBody User user){
//        return this.userService.update(id, user);
//    }
//
//    @DeleteMapping(value="/{id}")
//    public void delete(@PathVariable int id){
//        this.userService.delete(id);
//    }

}
