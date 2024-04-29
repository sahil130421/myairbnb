package com.airbnb.controller;

import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.payload.TokenResponse;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto) {
        PropertyUser propertyUser = userService.addUser(propertyUserDto);
        if (propertyUser != null) {
            return new ResponseEntity<>("Registration Successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/verify")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto) {
        String token = userService.verifyLogin(loginDto);
        if (token != null) {
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Gandi Mara", HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserLoggedIn(@AuthenticationPrincipal PropertyUser user){
        return user;
    }
}


