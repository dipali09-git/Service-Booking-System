package com.example.ServiceBookingSystemProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ServiceBookingSystemProject.DTO.CompanyDTO;


@Controller
@RequestMapping("/register")
public class RegistrationController {

	  @GetMapping("/company")
	    public String showCompanyRegistrationPage(Model model) {
	        // Add a new CompanyDTO object to the model for form binding
	        model.addAttribute("companySignupDTO", new CompanyDTO());
	        return "companyreg"; // Ensure this file exists as companyreg.html in src/main/resources/templates/
	    }
    
    // Other registration view mappings...
    @GetMapping("/user")
    public String showUserRegistrationPage() {
        return "User_Register"; // Renders companyRegister.html
    }
}
