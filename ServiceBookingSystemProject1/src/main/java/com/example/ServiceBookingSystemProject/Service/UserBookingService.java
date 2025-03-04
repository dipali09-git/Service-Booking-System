package com.example.ServiceBookingSystemProject.Service;


import com.example.ServiceBookingSystemProject.DTO.BookingDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import com.example.ServiceBookingSystemProject.Entity.User;
import com.example.ServiceBookingSystemProject.Repository.BookingRepository;
import com.example.ServiceBookingSystemProject.Repository.ServiceRepository;
import com.example.ServiceBookingSystemProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserBookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    // Creates a booking for a user using the userId (from path) and the BookingDTO
    public Booking createBooking(Long userId, BookingDTO bookingDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ServiceEntity service = serviceRepository.findById(bookingDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setService(service);
        booking.setBookingDate(LocalDateTime.now()); // Set current timestamp as booking time
        booking.setServiceDate(bookingDTO.getServiceDate());
        booking.setStatus(bookingDTO.getStatus() != null ? bookingDTO.getStatus() : "PENDING");
        return bookingRepository.save(booking);
    }
    
    // Retrieves the booking history for a given user
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
