package com.example.ServiceBookingSystemProject.Service;

import com.example.ServiceBookingSystemProject.DTO.BookingDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Repository.BookingRepository;
import com.example.ServiceBookingSystemProject.Repository.ServiceRepository;
import com.example.ServiceBookingSystemProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        // Set the user and service
        booking.setUser(userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        booking.setService(serviceRepository.findById(bookingDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));

        booking.setBookingDate(LocalDateTime.now()); // current timestamp
        booking.setServiceDate(bookingDTO.getServiceDate());
        booking.setStatus(bookingDTO.getStatus() != null ? bookingDTO.getStatus() : "PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByCompanyId(Long companyId) {
        return bookingRepository.findBookingsByCompanyId(companyId);
    }

    public Booking updateBookingStatus(Long bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
