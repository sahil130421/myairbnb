package com.airbnb.repository;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByPropertyAndPropertyUser(Property property, PropertyUser propertyUser);
  List<Review>findByPropertyUser(PropertyUser PropertyUser);
}
