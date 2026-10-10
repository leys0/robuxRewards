package org.example.fakerobuxsite.controller;

import jakarta.servlet.http.HttpSession;
import org.example.fakerobuxsite.model.Admin;
import org.example.fakerobuxsite.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin")
    public String adminLoginPage(HttpSession session) {
        if (session.getAttribute("adminLogged") != null) {
            return "redirect:/admin/painel.html";
        }
        return "redirect:/admin/login.html";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                session.setAttribute("adminLogged", admin.getUsername());
                return "redirect:/admin/painel.html";
            }
        }

        return "redirect:/admin/login.html?error=true";
    }

    @GetMapping("/admin/painel")
    public String painel(HttpSession session) {
        if (session.getAttribute("adminLogged") == null) {

            return "redirect:/";
        }

        return "redirect:/admin/painel.html";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login.html";
    }
}