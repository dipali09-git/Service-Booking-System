package com.example.ServiceBookingSystemProject.Controller;


import com.example.ServiceBookingSystemProject.DTO.BookingDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Service.UserBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private UserBookingService userBookingService;

    // Book a service for a user; userId is provided via the URL, and booking details in the request body.
    @PostMapping("/{userId}/book")
    public ResponseEntity<Booking> bookService(@PathVariable Long userId, @RequestBody BookingDTO bookingDTO) {
        Booking booking = userBookingService.createBooking(userId, bookingDTO);
        return ResponseEntity.ok(booking);
    }
    
    // Retrieve a user's booking history
    @GetMapping("/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = userBookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }
}

