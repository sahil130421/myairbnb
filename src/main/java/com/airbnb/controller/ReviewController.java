package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.exception.AlreadyExistException;
import com.airbnb.exception.ResourceNotFound;
import com.airbnb.payload.ReviewDto;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.repository.ReviewRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")


public class ReviewController {
    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    private PropertyUserRepository propertyUserRepository;


    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository, PropertyUserRepository propertyUserRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.propertyUserRepository = propertyUserRepository;
    }

    @PostMapping("/{propertyId}")
   public ResponseEntity<String>addReviews(
            @PathVariable long propertyId,
           @RequestBody Review review,
          @AuthenticationPrincipal PropertyUser propertyUser){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.orElseThrow(() -> new ResourceNotFound("PropertyNotFound"));
        Optional<Review> reviewIsPresent = reviewRepository.findByPropertyAndPropertyUser(property, propertyUser);
        if (reviewIsPresent.isPresent()){
            throw new AlreadyExistException("Review already exist");
        }


        review.setProperty(property);
        review.setPropertyUser(propertyUser);
        reviewRepository.save(review);

        return ResponseEntity.ok("Review added Successfully");

    }
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>>findByPropertyUser(@AuthenticationPrincipal PropertyUser propertyUser ){
        List<Review> reviews = reviewRepository.findByPropertyUser(propertyUser);
        return ResponseEntity.ok(reviews);

    }

}
