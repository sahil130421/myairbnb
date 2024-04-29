package com.airbnb.controller;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.FavouriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {
    private final FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }
    @PostMapping
    public ResponseEntity<Favourite>addFavourite(@RequestBody Favourite favourite,
                                                  @AuthenticationPrincipal PropertyUser propertyUser){
        favourite.setPropertyUser(propertyUser);
        Favourite saved = favouriteRepository.save(favourite);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
