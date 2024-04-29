package com.airbnb.service.impl;

import com.airbnb.entity.*;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.*;
import com.airbnb.service.PropertyService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private LocationRepository locationRepository;
    private ReviewRepository reviewRepository;
    private FavouriteRepository favouriteRepository;
    private ModelMapper modelMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, CountryRepository countryRepository, LocationRepository locationRepository, ReviewRepository reviewRepository, FavouriteRepository favouriteRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
        this.reviewRepository = reviewRepository;
        this.favouriteRepository = favouriteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PropertyDto addProperty(PropertyDto propertyDto) {
        Country country = countryRepository.findByCountryName(propertyDto.getCountry());
        if (country==null){
            country=new Country();
            country.setCountryName(propertyDto.getCountry());
            countryRepository.save(country);
        }
        Location location = locationRepository.findByLocationName(propertyDto.getLocation());
        if (location==null){
            location=new Location();
            location.setLocationName(propertyDto.getLocation());
            locationRepository.save(location);
        }

        Property property=new Property();
        property.setPropertyName(propertyDto.getPropertyName());
        property.setCountry(country);
        property.setLocation(location);
        property.setGuests(propertyDto.getGuests());
        property.setBedrooms(propertyDto.getBedrooms());
        property.setBathrooms(propertyDto.getBathrooms());
        property.setNightlyPrice(propertyDto.getNightlyPrice());
         propertyRepository.save(property);
         return mapToDto(property);
    }

    @Override

    public void deleteByPropertyId(long propertyId) {
//        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
//        if (propertyOptional.isPresent()) {
//            Property property = propertyOptional.get();
//
//            // Delete associated reviews
//            List<Review> reviews = property.getReviews();
//            for (Review review : reviews) {
//                reviewRepository.delete(review);
//            }
//            List<Favourite> favourites = property.getFavourites();
//            for (Favourite favourite:favourites){
//                favouriteRepository.delete(favourite);
//            }
                 propertyRepository.deleteById(propertyId);
//        } else {
//            throw new IllegalArgumentException("Property not delete");
//
//        }
    }

    Property mapToEntity(PropertyDto propertyDto){
        Property property = modelMapper.map(propertyDto, Property.class);
        return property;
    }
    PropertyDto mapToDto(Property property){
        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);
        return propertyDto;
    }
}
