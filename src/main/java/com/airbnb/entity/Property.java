package com.airbnb.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "bedrooms", nullable = false, length = 20)
    private String bedrooms;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Review> reviews;
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Favourite> favourites;
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Image> images;




    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }
}