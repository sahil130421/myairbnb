package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.time}")
    private int expiryTime;
    private Algorithm algorithm;
    private final static String USER_NAME="userName";
    @PostConstruct
    public void postConstruct(){
        algorithm= Algorithm.HMAC256(algorithmKey);
    }
    public String generateToken(PropertyUser propertyUser){
     return    JWT.create().
                withClaim(USER_NAME,propertyUser.getUserName()).
                withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).
                withIssuer(issuer).
                sign(algorithm);
    }
    public String getUserName(String token){
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        String username = jwt.getClaim(USER_NAME).asString();
        return username;

    }
}
