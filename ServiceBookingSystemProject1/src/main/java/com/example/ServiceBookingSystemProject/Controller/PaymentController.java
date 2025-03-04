package com.example.ServiceBookingSystemProject.Controller;


import com.example.ServiceBookingSystemProject.DTO.PaymentDTO;
import com.example.ServiceBookingSystemProject.Entity.Payment;
import com.example.ServiceBookingSystemProject.Service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private UserPaymentService userPaymentService;

    // Process a payment for a booking by a user
    @PostMapping("/{userId}/pay")
    public ResponseEntity<Payment> processPayment(@PathVariable Long userId, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = userPaymentService.processPayment(userId, paymentDTO);
        return ResponseEntity.ok(payment);
    }
    
    // Retrieve the payment history for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Payment>> getUserPayments(@PathVariable Long userId) {
        List<Payment> payments = userPaymentService.getPaymentsByUser(userId);
        return ResponseEntity.ok(payments);
    }
}
