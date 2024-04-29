package com.airbnb.payload;

import com.airbnb.entity.Country;
import com.airbnb.entity.Location;


public class PropertyDto {
    private Long id;
    private String propertyName;
    private String bedrooms;
    private Integer bathrooms;
    private Integer nightlyPrice;
    private Integer guests;
    private  String country;
    private String location;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Integer getNightlyPrice() {
        return nightlyPrice;
    }

    public void setNightlyPrice(Integer nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}