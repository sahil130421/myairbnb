package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.airbnb.exception.ResourceNotFound;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService {
    private PropertyUserRepository propertyUserRepository;
    private JwtService jwtService;

    public UserService(PropertyUserRepository propertyUserRepository, JwtService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser propertyUser=new PropertyUser();
        propertyUser.setFirstName(propertyUserDto.getFirstName());
        propertyUser.setLastName(propertyUserDto.getLastName());
        propertyUser.setEmail(propertyUserDto.getEmail());
        propertyUser.setUserName(propertyUserDto.getUserName());
        propertyUser.setUserRole("ROLE-USER");
        //propertyUser.setPassword(new BCryptPasswordEncoder().encode(propertyUser.getPassword()));
        propertyUser.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        return propertyUserRepository.save(propertyUser);

    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> byUserName = propertyUserRepository.findByUserName(loginDto.getUserName());
        Optional<PropertyUser> byEmail = propertyUserRepository.findByEmail(loginDto.getEmail());

        PropertyUser propertyUser = byEmail.orElse(byUserName.orElse(null));

        if (propertyUser != null && BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())) {
            return jwtService.generateToken(propertyUser);
        } else {
            throw new ResourceNotFound("UserName and email not found or incorrect password");
        }
    }
}
