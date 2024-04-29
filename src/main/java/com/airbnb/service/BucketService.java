package com.airbnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {

            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

    public String uploadPdfFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        try {
            String filename = "booking-confirmation-" + System.currentTimeMillis() + ".pdf";

            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + filename);
            file.transferTo(convFile);
            amazonS3.putObject(bucketName, filename, convFile);
            return amazonS3.getUrl(bucketName,filename).toString();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload PDF file", e);
        }


    }
}