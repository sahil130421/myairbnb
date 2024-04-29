package com.airbnb.controller;

import com.airbnb.entity.Image;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImageRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import com.airbnb.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private ImageRepository imageRepository;
    private ImageService imageService;

    public ImageController(BucketService bucketService, PropertyRepository propertyRepository, ImageRepository imageRepository, ImageService imageService) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }
    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addImage(@RequestParam MultipartFile file,
                                      @PathVariable String bucketName,
                                      @PathVariable long propertyId,
                                      @AuthenticationPrincipal PropertyUser propertyUser
    ) {
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Property property = propertyRepository.findById(propertyId).get();
        Image img = new Image();
        img.setImageUrl(imageUrl);
        img.setProperty(property);
        img.setPropertyUser(propertyUser);
        Image saveedImageDetails = imageRepository.save(img);

        return new ResponseEntity<>(saveedImageDetails, HttpStatus.OK);
    }
    @GetMapping("/{propertyId}")
    public ResponseEntity<List<Image>> getPropertyImages(@PathVariable Long propertyId) {
        List<Image> imageUrlsByPropertyId = imageService.getImageUrlsByPropertyId(propertyId);
        return new ResponseEntity<>(imageUrlsByPropertyId,HttpStatus.OK);

    }
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Map<String, String>>> getImageUrlsWithUserDetailsByPropertyId(@PathVariable Long propertyId) {
        List<Map<String, String>> imageUrlDetails = imageService.getImageUrlsWithUserDetailsByPropertyId(propertyId);
        return ResponseEntity.ok().body(imageUrlDetails);
    }
    @DeleteMapping("/{imageId}")
    public ResponseEntity<String>deleteImage(@PathVariable long imageId){
        imageService.deleteImage(imageId);
        return new ResponseEntity<>("Image is deleted",HttpStatus.OK);

    }


}
