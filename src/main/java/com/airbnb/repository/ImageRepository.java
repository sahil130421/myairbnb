package com.airbnb.repository;

import com.airbnb.entity.Image;
import com.airbnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPropertyId(Long propertyId);
    @Query("SELECT i.imageUrl AS imageUrl, pu.userName AS userName " +
            "FROM Image i " +
            "JOIN i.property p " +
            "JOIN PropertyUser pu " +
            "WHERE p.id = :propertyId")
    List<Map<String, String>> getImageUrlsWithUserDetailsByPropertyId(@Param("propertyId") Long propertyId);
}


