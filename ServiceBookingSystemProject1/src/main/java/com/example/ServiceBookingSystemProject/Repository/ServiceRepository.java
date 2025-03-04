package com.example.ServiceBookingSystemProject.Repository;



import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    // Retrieve all services provided by a specific company
    List<ServiceEntity> findByCompanyCompanyId(Long companyId);
    List<ServiceEntity> findByServiceNameContainingIgnoreCase(String serviceName);

}

