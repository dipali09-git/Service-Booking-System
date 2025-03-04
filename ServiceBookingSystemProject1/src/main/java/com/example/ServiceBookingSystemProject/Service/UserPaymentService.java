package com.example.ServiceBookingSystemProject.Service;

import com.example.ServiceBookingSystemProject.DTO.PaymentDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Entity.Payment;
import com.example.ServiceBookingSystemProject.Entity.User;
import com.example.ServiceBookingSystemProject.Repository.BookingRepository;
import com.example.ServiceBookingSystemProject.Repository.PaymentRepository;
import com.example.ServiceBookingSystemProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Processes a payment for a booking on behalf of a user
    public Payment processPayment(Long userId, PaymentDTO paymentDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setBooking(booking);
        // Optionally, if Payment has a user field, you can set it here:
        // payment.setUser(user);
        return paymentRepository.save(payment);
    }
    
    // Retrieves the payment history for a given user by traversing from Payment → Booking → User
    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findPaymentsByUserId(userId);
    }
}

