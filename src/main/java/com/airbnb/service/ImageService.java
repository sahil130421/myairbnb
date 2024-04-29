package com.airbnb.service;

import com.airbnb.entity.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ImageService {

    public List<Image> getImageUrlsByPropertyId(Long propertyId);
    public List<Map<String, String>> getImageUrlsWithUserDetailsByPropertyId(Long propertyId);
    public void deleteImage(Long imageId);
}
