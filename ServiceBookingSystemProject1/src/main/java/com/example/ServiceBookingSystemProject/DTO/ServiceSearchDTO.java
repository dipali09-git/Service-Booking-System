package com.example.ServiceBookingSystemProject.DTO;


public class ServiceSearchDTO {
    // The search query, e.g., service name or keywords
    private String query;

    public ServiceSearchDTO() {}

    public ServiceSearchDTO(String query) {
        this.query = query;
    }

    // Getter and setter
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}

