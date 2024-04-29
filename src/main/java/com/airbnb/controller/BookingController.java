package com.airbnb.controller;

import com.airbnb.entity.Booking;
import com.airbnb.entity.FileUtil;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/booking")

public class BookingController {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private BookingService bookingService;
    private  PdfGeneratorService pdfGeneratorService;
    private NewPdfGenerateService newPdfGenerateService;
    private BucketService bucketService;
    private FileUtil fileUtil;
    private SmsService smsService;
    private EmailService emailService;


    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, BookingService bookingService, PdfGeneratorService pdfGeneratorService, NewPdfGenerateService newPdfGenerateService, BucketService bucketService, FileUtil fileUtil, SmsService smsService, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.bookingService = bookingService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.newPdfGenerateService = newPdfGenerateService;
        this.bucketService = bucketService;
        this.fileUtil = fileUtil;

        this.smsService = smsService;
        this.emailService = emailService;
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<?> addBooking(@RequestBody Booking booking,
                                              @AuthenticationPrincipal PropertyUser propertyUser,
                                              @PathVariable long propertyId
    ) throws IOException {
        booking.setPropertyUser(propertyUser);
        Property property = propertyRepository.findById(propertyId).get();
        Integer nightlyPrice = property.getNightlyPrice();
        long totalNights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setTotalNights((int) totalNights);
        long totalPrice = nightlyPrice * totalNights;
        booking.setProperty(property);
        booking.setTotalPrice((int) totalPrice);
        Booking booked = bookingRepository.save(booking);
        String path = "D://3rd oct//booking-confirm"+booked.getId()+".pdf";

        //create pdf with booking conformation
        boolean b = newPdfGenerateService.generatePDF(booked, path);
        if (b) {
            try {
                MultipartFile xyz = FileUtil.xyz(path, "D://3rd oct//booking-confirm"+booked.getId()+".pdf");
                String uploadResult = bucketService.uploadPdfFile(xyz, "myairbnb9337");
                smsService.sendSms("+916370269337","Your Booking Is Confirmed and click here for more information"+uploadResult);
                emailService.sendEmail(booked.getEmail(),"Your Booking is Confirmed",uploadResult);
                if (uploadResult.startsWith("Failed")) {
                    return new ResponseEntity<>(uploadResult, HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    return new ResponseEntity<>(booked, HttpStatus.CREATED);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }}else {
            return new ResponseEntity<>("Failed to Upload File",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}/generate-pdf")
    public ResponseEntity<String> generatePdfForBooking(@PathVariable("id") Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
        }

        try {
            String filePath = "D://3rd oct//booking.pdf"; // Provide the file path here
            pdfGeneratorService.generateBookingPdf(booking, "D://3rd oct//booking.pdf");
            return new ResponseEntity<>("PDF generated successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error generating PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
