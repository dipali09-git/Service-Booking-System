package com.example.ServiceBookingSystemProject.DTO;


import java.time.LocalDateTime;

public class PaymentDTO {
    private Long bookingId;
    private double amount;
    // Optionally, you could let the system capture paymentDate.
    private String paymentStatus; // e.g., "COMPLETED", "FAILED"

    public PaymentDTO() {}

    public PaymentDTO(Long bookingId, double amount, String paymentStatus) {
        this.bookingId     = bookingId;
        this.amount        = amount;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}

