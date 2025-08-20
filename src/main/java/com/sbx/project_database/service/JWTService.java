package com.sbx.project_database.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private  String verySecretKey = "";

    JWTService (){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSha256");
            SecretKey key = keyGenerator.generateKey();
            verySecretKey = Base64.getEncoder().encodeToString(key.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String userName) {

        Map<String, Object> claims = new HashMap<String, Object>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)) //this is 30 minutes
                .and()
                .signWith(getKey())
                .compact();
    }
    private Key getKey(){
        byte [] keyBytes = verySecretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
