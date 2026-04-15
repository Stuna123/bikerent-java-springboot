// Controller qui gère l'inscription et la connexion des utilisateurs
package com.bikerent.controller;

import com.bikerent.entity.User;
import com.bikerent.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    // Injection du service utilisateur
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Affiche le formulaire d'inscription
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Traite le formulaire d'inscription
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("successMessage", "Inscription réussie. Vous pouvez maintenant vous connecter.");
            model.addAttribute("user", new User());
            return "register";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }

    // Affiche la page de connexion
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}