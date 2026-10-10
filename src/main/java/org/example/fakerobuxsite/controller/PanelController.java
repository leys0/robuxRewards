package org.example.fakerobuxsite.controller;

import jakarta.servlet.http.HttpSession;
import org.example.fakerobuxsite.model.GlobalConfig;
import org.example.fakerobuxsite.model.User;
import org.example.fakerobuxsite.model.UserRole;
import org.example.fakerobuxsite.repository.GlobalConfigRepository;
import org.example.fakerobuxsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PanelController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GlobalConfigRepository globalConfigRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("adminLogged") != null) {
            return "redirect:/panel";
        }
        return "panel/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(username);
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("adminLogged", user.getUsername());
                session.setAttribute("userRole", user.getRole());
                return "redirect:/panel";
            }
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/panel")
    public  String panelPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("adminLogged");
        UserRole role = (UserRole) session.getAttribute("userRole");

        if (username == null) {
            return "redirect:/login";
        }

        Optional<GlobalConfig> configOpt = globalConfigRepository.findByUsername(username);
        configOpt.ifPresent(config -> model.addAttribute("config", config));

        model.addAttribute("isMaster", UserRole.MASTER.equals(role));
        model.addAttribute("userRole", role.toString());
        model.addAttribute("username", username);
    
        return "panel/panel";
    }

    @PostMapping("/panel/configs")
    public String salvarConfigs(@RequestParam String missionLink, @RequestParam String ttkUser, HttpSession session) {
        String username = session.getAttribute("adminLogged").toString();
        if (username == null) {
            return "redirect:/admin/login.html";
        }

        Optional<GlobalConfig> existingConfigOpt = globalConfigRepository.findByUsername(username);
        GlobalConfig config;
        if (existingConfigOpt.isPresent()) {
            config = existingConfigOpt.get();
            config.setMissionLink(missionLink);
            config.setTtkUser(ttkUser);
        } else {
            config = new GlobalConfig(username, missionLink, ttkUser);
        }
        globalConfigRepository.save(config);

        return "redirect:/panel?success=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}