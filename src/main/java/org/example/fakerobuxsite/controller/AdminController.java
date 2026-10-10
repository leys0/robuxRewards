package org.example.fakerobuxsite.controller;

import org.example.fakerobuxsite.model.Admin;
import org.example.fakerobuxsite.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin loginData) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(loginData.getUsername());

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(loginData.getPassword())) {
                return ResponseEntity.ok(Map.of("message", "Login realizado com sucesso!"));
            }
        }

        return ResponseEntity.status(401).body(Map.of("error", "Usuário ou senha inválidos"));
    }

}