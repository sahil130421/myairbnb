package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.entity.Location;
import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private PropertyRepository propertyRepository;
    private PropertyService propertyService;

    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService)
    {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<PropertyDto>addProperty(@RequestBody PropertyDto propertyDto){
        PropertyDto property = propertyService.addProperty(propertyDto);
       return new ResponseEntity<>(property,HttpStatus.CREATED);
    }
    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>>findProperty(@PathVariable String locationName){
        List<Property> propertyByLocation = propertyRepository.findPropertyByLocation(locationName);
        return new ResponseEntity<>(propertyByLocation, HttpStatus.OK);

    }
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<String>deleteProperty(@PathVariable long propertyId){
        propertyService.deleteByPropertyId(propertyId);
        return new ResponseEntity<>("Property is deleted",HttpStatus.OK);
    }
}
