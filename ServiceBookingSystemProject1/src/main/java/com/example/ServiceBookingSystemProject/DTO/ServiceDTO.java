package com.example.ServiceBookingSystemProject.DTO;


public class ServiceDTO {
    private String serviceName;
    private String description;
    private double price;
    private Long companyId;  // The company providing this service

    public ServiceDTO() {}

    public ServiceDTO(String serviceName, String description, double price, Long companyId) {
        this.serviceName = serviceName;
        this.description = description;
        this.price       = price;
        this.companyId   = companyId;
    }

    // Getters and setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

	public void setServiceId(Long serviceId) {
	
		
	}
}
