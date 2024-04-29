package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDto;

public interface PropertyService {

    PropertyDto addProperty(PropertyDto propertyDto);

    void deleteByPropertyId(long propertyId);
}
