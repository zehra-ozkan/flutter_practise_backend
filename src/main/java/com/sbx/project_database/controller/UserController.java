package com.sbx.project_database.controller;

import com.sbx.project_database.models.User;
import com.sbx.project_database.service.UserProfileService;
import com.sbx.project_database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping("/app_users")
@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private UserProfileService userProfileService;


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
                            "userId", user.getUser_id(),
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
    @GetMapping(value="/friends/top") //this will return the name and profile pictures of the top 10 friends
    public ResponseEntity<Map<String, Object>> getTop10Friends(@RequestHeader("Authorization") String token) {
        try{

            System.out.println("\nrequesting top 10 friends with token **" + token + "**");
            User person = userService.getUserByToken(token.substring(7)); //"Bearer plus one whitespace todo do better checking of substring here


            System.out.println("Accepted token with username " + person.getUserName());


            ArrayList<Map<String, String>> friendInfo =  userService.getUserTop10Friends(person);
            System.out.println("ArrayList is null" + friendInfo == null);
            return ResponseEntity.ok()
                    .body(Map.of(
                                "friends", friendInfo,
                            "success", true,
                            "count", friendInfo.size())
                    );

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of(
                            "friends", null,
                            "success", false,
                            "count", -1)
                    );

        }
    }

    @PostMapping(value="/friends/add" , consumes = MediaType.APPLICATION_JSON_VALUE) //todo adds twice!!!!!
    public ResponseEntity<Map<String, Object>> addFriends(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> requestBody) {
        try{

            System.out.println("\nrequesting top 10 friends with token **" + token + "**");
            User person = userService.getUserByToken(token.substring(7)); // this is who I am
            int reqId = (Integer) requestBody.get("userId");

            System.out.println("Request id is " + reqId);
            User requestedFriend = userService.getUserById(reqId);

            User result = userService.addUserFriend(person, requestedFriend);
            System.out.println("before returning friend count = " + person.getFriends().size());

            String photo = "";
            if(requestedFriend.getProfile() != null) {
                photo = Base64.getEncoder().encodeToString(result.getProfile().getPhoto());
            }

            return ResponseEntity.ok()
                    .body(Map.of(
                            "name", result.getUserName(),
                            "success", true,
                            "profile" , photo
                    ));

        }catch (Exception e){
            System.out.println("We are in error " + e);
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of(
                            "friends", null,
                            "success", false,
                            "count", -1)
                    );

        }
    }

    @PostMapping(value="/friends/addReq" , consumes = MediaType.APPLICATION_JSON_VALUE) //todo adds twice!!!!!
    public ResponseEntity<Map<String, Object>> addFriendsReq(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> requestBody) {

        try{
            System.out.println("\nrequesting top 10 friends with token **" + token + "**");
            User person = userService.getUserByToken(token.substring(7)); // this is who I am
            int reqId = (Integer) requestBody.get("userId");

            System.out.println("Request id is " + reqId);
            User requestedFriend = userService.getUserById(reqId);

            String photo = "";
            if(requestedFriend.getProfile() != null) {
                photo = Base64.getEncoder().encodeToString(requestedFriend.getProfile().getPhoto());
            }

            return ResponseEntity.ok()
                    .body(Map.of(
                            "name", requestedFriend.getUserName(),
                            "success", true,
                            "profile" , photo
                    ));

        }catch (Exception e){
            System.out.println("We are in error " + e);
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of(
                            "friends", null,
                            "success", false,
                            "count", -1)
                    );

        }
    }
    @GetMapping(value="/friends/all")
    public ResponseEntity<Map<String, Object>> getAllFriends(@RequestHeader("Authorization") String token) {

        return ResponseEntity.status(401)
                .body(Map.of(
                        "message", "ccc",
                        "success", false));

    }



}
