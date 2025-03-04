package com.example.ServiceBookingSystemProject.Service;


import com.example.ServiceBookingSystemProject.DTO.PaymentDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Entity.Payment;
import com.example.ServiceBookingSystemProject.Repository.BookingRepository;
import com.example.ServiceBookingSystemProject.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public Payment processPayment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setBooking(booking);
        return paymentRepository.save(payment);
    }
    
    public List<Payment> getPaymentsByCompanyId(Long companyId) {
        return paymentRepository.findPaymentsByCompanyId(companyId);
    }
}
