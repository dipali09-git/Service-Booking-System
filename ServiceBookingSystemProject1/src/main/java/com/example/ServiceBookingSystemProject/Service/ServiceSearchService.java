package com.example.ServiceBookingSystemProject.Service;

import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import com.example.ServiceBookingSystemProject.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSearchService {

    @Autowired
    private ServiceRepository serviceRepository;

    // Searches for services whose names contain the provided query (case-insensitive)
    public List<ServiceEntity> searchServices(String query) {
        return serviceRepository.findByServiceNameContainingIgnoreCase(query);
    }

    // Retrieve a single service by its ID
    public ServiceEntity getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

	public List<ServiceEntity> getAllServices() {
		
		return null;
	}
}
