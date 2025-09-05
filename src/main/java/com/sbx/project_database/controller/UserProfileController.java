package com.sbx.project_database.controller;


import com.sbx.project_database.models.User;
import com.sbx.project_database.models.UserProfile;
import com.sbx.project_database.service.UserProfileService;
import com.sbx.project_database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping(value="/user_profiles")
@RestController
@CrossOrigin
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping(value = "/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestHeader("Authorization") String token) {


        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false));
    }

    @PostMapping(value="/updatePic")
    public ResponseEntity<Map<String, Object>> updateProfilePic(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("inside the update pic request");
        System.out.println("\nrequesting Picture update with token **" + token + "**");

        User user = userService.getUserByToken(token.substring(7)); //"Bearer plus one whitespace todo do better checking of substring here
        int userId = user.getUser_id();
        String userName = user.getUserName();

        System.out.println("requesting profile update for " + userName + " with user id = " + userId);
        UserProfile userProfile = user.getProfile();
        if(userProfile == null){
            System.out.println("No existing profile found. Creating new profile...");
            userProfile = new UserProfile();
            userProfile.setUser(user);
            user.setProfile(userProfile); // Also set it on the user side

        }
        userProfile.setPhoto(file.getBytes());
        try {
            System.out.println("user profile id is " + userProfile.getProfileUserId());
            UserProfile h =  userProfileService.addUserProfilePic(userProfile);

            System.out.println("User Profile added successfully pls pls pls");
            return ResponseEntity.ok(Map.of(
                    "success", true, //this is the boolean key
                    "message", "yippie" // Sending user ID to Flutter
            ));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of("message", "olmadı", "success", false));
        }
    }
}