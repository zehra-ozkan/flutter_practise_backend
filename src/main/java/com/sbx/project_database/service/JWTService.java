package com.sbx.project_database.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private  String verySecretKey = "";

    JWTService (){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            verySecretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
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
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 300)) //this is 30 minutes
                .and()
                .signWith(getKey())
                .compact();
    }
    private SecretKey getKey(){
        byte [] keyBytes = verySecretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        boolean expired = isTokenExpired(token);

        System.out.println("Token username: " + userName);
        System.out.println("UserDetails username: " + userDetails.getUsername());
        System.out.println("UserDetails class: " + userDetails.getClass().getName());
        System.out.println("UserDetails toString: " + userDetails.toString());

        System.out.println("expired: " + expired);
        System.out.println("userName: " + userName + "/////////////// detail userName : " + userDetails.getUsername());
        boolean k = (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        System.out.println("\nThe validation results in " + k);
        return k;
    }
    private boolean isTokenExpired(String token) {
        boolean k = extractExpiration(token).before(new Date());
        System.out.println("The token is expire: = " + k);
        return k;
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
