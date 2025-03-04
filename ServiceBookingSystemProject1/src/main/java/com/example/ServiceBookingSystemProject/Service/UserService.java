package com.example.ServiceBookingSystemProject.Service;


import com.example.ServiceBookingSystemProject.DTO.UserDTO;
import com.example.ServiceBookingSystemProject.Entity.User;
import com.example.ServiceBookingSystemProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(UserDTO userDTO) {
        // Optionally check for duplicate email before registration
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());  // Consider hashing in production
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        return userRepository.save(user);
    }
    
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }
}
