package com.example.ServiceBookingSystemProject.Service;

import com.example.ServiceBookingSystemProject.DTO.ServiceDTO;
import com.example.ServiceBookingSystemProject.Entity.Company;
import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import com.example.ServiceBookingSystemProject.Repository.CompanyRepository;
import com.example.ServiceBookingSystemProject.Repository.ServiceRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceService {
    
    // Updated to store files in the 'images' folder under static
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    public ServiceEntity createService(ServiceDTO serviceDTO, MultipartFile file) throws IOException {
        ServiceEntity service = new ServiceEntity();
        service.setServiceName(serviceDTO.getServiceName());
        service.setDescription(serviceDTO.getDescription());
        service.setPrice(serviceDTO.getPrice());
        
        // Ensure the company exists
        Company company = companyRepository.findById(serviceDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        service.setCompany(company);
        
        // Handle file upload
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            // Create the directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());
            service.setImageFileName(fileName);
        }
        
        ServiceEntity savedService = serviceRepository.save(service);
        System.out.println("Service saved successfully with ID: " + savedService.getServiceId());
        return savedService;
    }
    
    public List<ServiceEntity> getServicesByCompanyId(Long companyId) {
        return serviceRepository.findByCompanyCompanyId(companyId);
    }
    @Transactional
    public ServiceEntity updateService(Long serviceId, String serviceName, String description, double price, MultipartFile file) throws IOException {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Update service details
        service.setServiceName(serviceName);
        service.setDescription(description);
        service.setPrice(price);

        // Handle file upload
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            // Validate file type
            if (!fileName.matches(".*\\.(png|jpg|jpeg)$")) {
                throw new RuntimeException("Invalid file type. Only PNG, JPG, and JPEG are allowed.");
            }

            // Ensure upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Delete old image if exists
            if (service.getImageFileName() != null) {
                Path oldFilePath = uploadPath.resolve(service.getImageFileName());
                Files.deleteIfExists(oldFilePath);
            }

            // Save new file
            Files.write(filePath, file.getBytes());

            // Update entity with new file name
            service.setImageFileName(uniqueFileName);
        }

        return serviceRepository.save(service);
    }

    
    public void deleteService(Long companyId, Long serviceId) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        if (!service.getCompany().getCompanyId().equals(companyId)) {
            throw new RuntimeException("Unauthorized: This service does not belong to your company");
        }
        serviceRepository.delete(service);
    }
    
   
    
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceEntity getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

}
