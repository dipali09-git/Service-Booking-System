package com.example.ServiceBookingSystemProject.Controller;

import com.example.ServiceBookingSystemProject.DTO.CompanyDTO;
import com.example.ServiceBookingSystemProject.DTO.LoginDTO;
import com.example.ServiceBookingSystemProject.DTO.ServiceDTO;
import com.example.ServiceBookingSystemProject.Entity.Booking;
import com.example.ServiceBookingSystemProject.Entity.Company;
import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import com.example.ServiceBookingSystemProject.Service.BookingService;
import com.example.ServiceBookingSystemProject.Service.CompanyService;
import com.example.ServiceBookingSystemProject.Service.ServiceService;
import jakarta.servlet.http.HttpSession;

import org.springframework.boot.logging.structured.ElasticCommonSchemaProperties.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/companies")
@SessionAttributes("loggedInCompany")
public class CompanyModuleController {

    private final CompanyService companyService;
    private final ServiceService serviceService;
    private final BookingService bookingService;

    public CompanyModuleController(CompanyService companyService, ServiceService serviceService, BookingService bookingService) {
        this.companyService = companyService;
        this.serviceService = serviceService;
        this.bookingService = bookingService;
    }

    // This method ensures that the session attribute "loggedInCompany" is available in the model.
    @ModelAttribute("loggedInCompany")
    public Company getLoggedInCompany() {
        return null;  // Initially null
    }

    // ====== Authentication ======
    @GetMapping("/login")
    public String showCompanyLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "companylogin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        Company company = companyService.loginCompany(loginDTO.getEmail(), loginDTO.getPassword());
        if (company == null) {
            redirectAttributes.addFlashAttribute("loginStatus", "error");
            return "redirect:/companies/login";
        }
        // Add company to the model so Spring stores it in the session
        model.addAttribute("loggedInCompany", company);
        System.out.println("Login successful. Company stored in session: " + company);
        return "redirect:/companies/dashboard";
    }

    @GetMapping("/register")
    public String showCompanyRegistrationPage(Model model) {
        model.addAttribute("companyDTO", new CompanyDTO());
        return "companyreg";
    }

    @PostMapping("/register")
    public String registerCompany(@ModelAttribute("companyDTO") CompanyDTO companyDTO,
                                  RedirectAttributes redirectAttributes) {
        Company company = companyService.registerCompany(companyDTO);
        if (company != null) {
            redirectAttributes.addFlashAttribute("registrationStatus", "success");
        } else {
            redirectAttributes.addFlashAttribute("registrationStatus", "error");
        }
        return "redirect:/companies/register";
    }

    // ====== Dashboard Navigation ======
    @GetMapping("/dashboard")
    public String companyDashboard() {
        return "companydashboard";
    }

    @GetMapping("/viewbookings")
    public String viewBookings(Model model, @ModelAttribute("loggedInCompany") Company company) {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings == null) {
            System.out.println("No bookings found. Redirecting to login...");
            return "redirect:/companies/login";
        }
        model.addAttribute("bookings", bookings);
        return "ViewBookings";
    }

    // ====== Booking Management ======
    @PostMapping("/approveBooking")
    public String approveBooking(@RequestParam(required = false) Long bookingId,
                                 RedirectAttributes redirectAttributes) {
        if (bookingId == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid Booking ID.");
            return "redirect:/companies/viewbookings";
        }
        bookingService.updateBookingStatus(bookingId, "Approved");
        return "redirect:/companies/viewbookings";
    }

    @PostMapping("/rejectBooking")
    public String rejectBooking(@RequestParam Long bookingId) {
        bookingService.updateBookingStatus(bookingId, "Rejected");
        return "redirect:/companies/viewbookings";
    }

    // ====== Service Management ======
    // Display the Create Ad form (GET)
    @GetMapping("/postAd")
    public String showCreateAdForm(Model model, @ModelAttribute("loggedInCompany") Company company) {
        System.out.println("showCreateAdForm: loggedInCompany = " + company);
        if (company == null) {
            return "redirect:/companies/login";
        }
        model.addAttribute("serviceDTO", new ServiceDTO());
        return "createAdForm";
    }

    // Process the Create Ad form submission (POST)
    @PostMapping("/postAd")
    public String createService(@ModelAttribute ServiceDTO serviceDTO,
                                @RequestParam("file") MultipartFile file,
                                @ModelAttribute("loggedInCompany") Company company,
                                RedirectAttributes redirectAttributes) {
        if (company == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to post an ad.");
            return "redirect:/companies/postAd";
        }
        serviceDTO.setCompanyId(company.getCompanyId());
        try {
            serviceService.createService(serviceDTO, file);
            redirectAttributes.addFlashAttribute("success", "Ad posted successfully!");
            return "redirect:/companies/manageads";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to post ad: " + e.getMessage());
            return "redirect:/companies/postAd";
        }
    }

    @GetMapping("/manageads")
    public String showManageAdsPage(Model model, @ModelAttribute("loggedInCompany") Company company) {
        if (company == null) {
            return "redirect:/companies/login";
        }
        List<ServiceEntity> services = serviceService.getServicesByCompanyId(company.getCompanyId());
        model.addAttribute("services", services);
        return "manageads";
    }
    @GetMapping("/updateAd")
    public String showUpdateForm(@RequestParam("serviceId") Long serviceId, Model model) {
        ServiceEntity service = serviceService.getServiceById(serviceId);
        model.addAttribute("service", service);
        return "updateAd";  // This refers to updateAd.html
    }

    // Handle Service Update
    @PostMapping("/updateAd")
    public String updateService(@RequestParam("serviceId") Long serviceId,
                                @RequestParam("serviceName") String serviceName,
                                @RequestParam("description") String description,
                                @RequestParam("price") double price,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            // Call service to update
            serviceService.updateService(serviceId, serviceName, description, price, file);
            redirectAttributes.addFlashAttribute("success", "Ad updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to update ad: " + e.getMessage());
            return "redirect:/companies/updateAd?serviceId=" + serviceId;
        }
        return "redirect:/companies/manageads";
    }

    @PostMapping("/deleteAd")
    public String deleteAd(@RequestParam Long serviceId,
                           @ModelAttribute("loggedInCompany") Company company,
                           RedirectAttributes redirectAttributes) {
        try {
            if (company == null) {
                redirectAttributes.addFlashAttribute("error", "You must be logged in to delete an ad.");
                return "redirect:/companies/manageads";
            }
            serviceService.deleteService(company.getCompanyId(), serviceId); // Pass both companyId and adId
            redirectAttributes.addFlashAttribute("success", "Ad deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete ad.");
        }
        return "redirect:/companies/manageads";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/companies/login";
    }
}
