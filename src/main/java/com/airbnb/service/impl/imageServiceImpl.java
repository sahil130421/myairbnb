package com.airbnb.service.impl;

import com.airbnb.entity.Image;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImageRepository;
import com.airbnb.service.ImageService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class imageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    private AmazonS3 amazonS3;

    public imageServiceImpl(ImageRepository imageRepository, AmazonS3 amazonS3) {
        this.imageRepository = imageRepository;
        this.amazonS3 = amazonS3;
    }


    @Override
    public List<Image> getImageUrlsByPropertyId(Long propertyId) {
        List<Image> images = imageRepository.findByPropertyId(propertyId);
      return images;
    }

    @Override
    public List<Map<String, String>> getImageUrlsWithUserDetailsByPropertyId(Long propertyId) {
        return imageRepository.getImageUrlsWithUserDetailsByPropertyId(propertyId);

    }

    @Override
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElse(null);

        if (image != null) {
            try {

                String imageUrl = image.getImageUrl();
                String[] split = imageUrl.split("/");

                for (String x:split)
                {
                    System.out.println(x);
                }

                System.out.println(split.length);

                String s = split[split.length-1];
                System.out.println(s);
                amazonS3.deleteObject(new DeleteObjectRequest("myairbnb9337",s));
                imageRepository.delete(image);
            } catch (AmazonServiceException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to delete image from S3 bucket: " + e.getMessage());
            }
        } else {
            throw new EntityNotFoundException("Image not found with ID: " + imageId);
        }

    }
}
