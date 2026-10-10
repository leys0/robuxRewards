package org.example.fakerobuxsite.controller;

import jakarta.servlet.http.HttpSession;
import org.example.fakerobuxsite.model.UserRole;
import org.example.fakerobuxsite.model.User;
import org.example.fakerobuxsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/user/register")
    public String registerUser(
            @RequestParam String username, @RequestParam String email,
            @RequestParam String password, @RequestParam UserRole role, HttpSession session) {
        UserRole loggedRole = (UserRole) session.getAttribute("userRole");
        if (!UserRole.MASTER.equals(loggedRole)) {
            return "redirect:/login?error=unauthorized";
        }

        if (userRepository.findByUsername(username).isPresent() ||
                userRepository.findByEmail(email).isPresent()) {
            return "redirect:/panel?error=exists";
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, role);
        userRepository.save(newUser);

        return "redirect:/panel?success=registered";
    }

}