package com.example.ServiceBookingSystemProject.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // This corresponds to home.html in src/main/resources/templates/
    }

    @GetMapping("/register")
    public String register() {
        return "selreg"; // Ensure register.html exists in templates
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Ensure login.html exists in templates
    }
}

