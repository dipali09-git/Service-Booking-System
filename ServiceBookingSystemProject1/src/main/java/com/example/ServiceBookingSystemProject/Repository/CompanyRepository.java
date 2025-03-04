package com.example.ServiceBookingSystemProject.Repository;


import com.example.ServiceBookingSystemProject.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // For login: find company by email
    Optional<Company> findByEmail(String email);
}

