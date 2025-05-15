package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/root")
public String rootRedirect() {
    return "redirect:/login";
}

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "Username atau Password salah");
        return "login";
    }

    @GetMapping("/register")
    public String registPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html
    }

   @PostMapping("/add-user")
public String addUser(@ModelAttribute User user, Model model) {
    try {
        userServices.saveUser(user); // Simpan user, bisa lempar exception kalau duplikat
        return "redirect:/login";
    } catch (RuntimeException e) {
        // Tangkap error duplikat dan kirim pesan ke view
        model.addAttribute("user", user);
        model.addAttribute("error", e.getMessage());
        return "register"; // kembali ke halaman register dengan pesan error
    }
}
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable String id) {
        userServices.deleteUser(id);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login?error";
        }

        User user = userOpt.get();
        model.addAttribute("name", user.getName());
        return "home"; // home.html
    }
}
