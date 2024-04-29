package com.airbnb.entity;


import jakarta.persistence.Entity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Service


public class FileUtil {

    public static MultipartFile xyz(String filePath, String fileName) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile(fileName,fileName, "application/pdf", input);

    }
}