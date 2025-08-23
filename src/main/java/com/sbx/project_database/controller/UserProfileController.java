package com.sbx.project_database.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value="/user_profiles")
@RestController
public class UserProfileController {

    @GetMapping(value = "/home")
    public ResponseEntity<Map<String, Object>> getProfile(@RequestHeader("Authorization") String token) {



        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false));
    }
}