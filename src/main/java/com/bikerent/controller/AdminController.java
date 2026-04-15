// Controller qui gère les pages de l'espace administrateur
package com.bikerent.controller;

import com.bikerent.service.AdminService;
import com.bikerent.service.ReservationService;
import com.bikerent.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final ReservationService reservationService;
    private final UserRepository userRepository;

    // Injection des dépendances nécessaires au dashboard admin
    public AdminController(AdminService adminService,
                           ReservationService reservationService,
                           UserRepository userRepository) {
        this.adminService = adminService;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    // Affiche le dashboard administrateur avec les statistiques principales
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("totalReservations", adminService.getTotalReservations());
        model.addAttribute("totalBikes", adminService.getTotalBikes());
        model.addAttribute("totalRevenue", adminService.getTotalRevenue());

        // Liste de toutes les réservations pour affichage détaillé
        model.addAttribute("reservations", reservationService.getAllReservations());

        // Liste de tous les utilisateurs
        model.addAttribute("users", userRepository.findAll());

        return "admin-dashboard";
    }
}