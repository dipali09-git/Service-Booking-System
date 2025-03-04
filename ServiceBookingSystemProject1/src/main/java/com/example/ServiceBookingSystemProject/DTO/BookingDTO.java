package com.example.ServiceBookingSystemProject.DTO;


import java.time.LocalDateTime;

public class BookingDTO {
    private Long userId;
    private Long serviceId;
    // You can let the system set bookingDate automatically.
    private LocalDateTime serviceDate; // The scheduled service time
    private String status; // e.g., "PENDING", "CONFIRMED"

    public BookingDTO() {}

    public BookingDTO(Long userId, Long serviceId, LocalDateTime serviceDate, String status) {
        this.userId      = userId;
        this.serviceId   = serviceId;
        this.serviceDate = serviceDate;
        this.status      = status;
    }

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    
    public LocalDateTime getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDateTime serviceDate) { this.serviceDate = serviceDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
