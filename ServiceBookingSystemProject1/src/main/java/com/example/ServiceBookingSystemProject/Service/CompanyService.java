package com.example.ServiceBookingSystemProject.Service;

import com.example.ServiceBookingSystemProject.DTO.CompanyDTO;
import com.example.ServiceBookingSystemProject.Entity.Company;
import com.example.ServiceBookingSystemProject.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company registerCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setEmail(companyDTO.getEmail());
        company.setPassword(companyDTO.getPassword());
        company.setPhone(companyDTO.getPhone());
        company.setAddress(companyDTO.getAddress());
        return companyRepository.save(company);
    }
    
    public Company loginCompany(String email, String password) {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        if (!company.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return company;
    }
    
    public Company getCompanyById(Long id) {
         return companyRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Company not found"));
    }
}

