package com.example.ServiceBookingSystemProject.Controller;

import com.example.ServiceBookingSystemProject.DTO.LoginDTO;
import com.example.ServiceBookingSystemProject.DTO.UserDTO;
import com.example.ServiceBookingSystemProject.Entity.ServiceEntity;
import com.example.ServiceBookingSystemProject.Entity.User;
import com.example.ServiceBookingSystemProject.Service.ServiceSearchService;
import com.example.ServiceBookingSystemProject.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ServiceSearchService serviceSearchService;

    // === Registration ===
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "User_Register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes) {
        User user = userService.registerUser(userDTO);
        if (user != null) {
            redirectAttributes.addFlashAttribute("registrationStatus", "success");
        } else {
            redirectAttributes.addFlashAttribute("registrationStatus", "error");
        }
        return "redirect:/users/register";
    }

    // === Login ===
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "userlogin"; 
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                            Model model,
                            HttpSession session) {
        User user = userService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
        if(user == null){
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "userlogin";
        }
        // Store the logged-in user in session
        session.setAttribute("loggedInUser", user);
        
        // Redirect to the /users/services page by default
        return "redirect:/users/services";
    }

    // === Dashboard: Services Listing ===
    // Displays all services by default
    @GetMapping("/services")
    public String listAllServices(Model model, HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        // Retrieve all services (or use a custom method if needed)
        List<ServiceEntity> services = serviceSearchService.getAllServices();
        model.addAttribute("services", services);
        model.addAttribute("query", ""); // For the search bar
        return "userdashboard"; // The page that shows services list + nav
    }

    // === Search Services ===
    @GetMapping("/services/search")
    public String searchServices(@RequestParam("query") String query,
                                 Model model,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        List<ServiceEntity> services = serviceSearchService.searchServices(query);
        model.addAttribute("services", services);
        model.addAttribute("query", query);
        return "userdashboard";
    }

    // === Book a Service (show the booking form) ===
    @GetMapping("/bookservice")
    public String showBookingForm(@RequestParam("serviceId") Long serviceId,
                                  Model model,
                                  HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        ServiceEntity service = serviceSearchService.getServiceById(serviceId);
        model.addAttribute("service", service);
        // Also add the user to the model
        model.addAttribute("loggedInUser", user);
        return "userbookingform";
    }

    // === Logout ===
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
