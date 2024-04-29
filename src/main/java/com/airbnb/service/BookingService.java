package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.airbnb.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service

public class BookingService {
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }
}
