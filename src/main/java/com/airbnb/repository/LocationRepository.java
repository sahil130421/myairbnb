package com.airbnb.repository;

import com.airbnb.entity.Country;
import com.airbnb.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findByLocationName(String LocationName);
}