// Controller qui gère l'affichage et l'enregistrement des réservations
package com.bikerent.controller;

import com.bikerent.entity.*;
import com.bikerent.repository.UserRepository;
import com.bikerent.service.BikeService;
import com.bikerent.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final BikeService bikeService;

    private final UserRepository userRepository;

    // Injection des services et repositories nécessaires
    public ReservationController(ReservationService reservationService,
                                 BikeService bikeService,
                                 UserRepository userRepository) {
        this.reservationService = reservationService;
        this.bikeService = bikeService;
        this.userRepository = userRepository;
    }

    // Affiche le formulaire de réservation pour un vélo donné
    @GetMapping("/reserve/{bikeId}")
    public String showReservationForm(@PathVariable Long bikeId, Model model) {
        Bike bike = bikeService.getBikeById(bikeId);
        model.addAttribute("bike", bike);
        model.addAttribute("reservation", new Reservation());
        return "reservation";
    }

    // Traite la soumission du formulaire
    @PostMapping("/reserve")
    public String reserveBike(@ModelAttribute Reservation reservation,
                              @RequestParam Long bikeId,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        Bike bike = bikeService.getBikeById(bikeId);

        // Récupérer l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        // Chercher l'utilisateur en base
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Associer la réservation à l'utilisateur
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.EN_ATTENTE);

        try {
            reservationService.saveReservation(reservation, bike);

            // Message temporaire affiché après redirection vers /bikes
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "La réservation a bien été effectuée avec succès."
            );

            return "redirect:/bikes";
        } catch (RuntimeException e) {
            model.addAttribute("bike", bike);
            model.addAttribute("reservation", reservation);
            model.addAttribute("errorMessage", e.getMessage());

            return "reservation";
        }
    }

    // Affiche l'historique des réservations de l'utilisateur connecté
    @GetMapping("/reservations")
    public String showUserReservations(Model model) {

        // Récupère l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        // Récupère uniquement ses réservations
        model.addAttribute("reservations", reservationService.getReservationsByUserEmail(email));

        return "reservations";
    }

    @GetMapping("/make-admin")
    @ResponseBody
    public String makeAdmin() {
        User user = userRepository.findByEmail("ftabora@test.com")
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return "Tu es maintenant ADMIN";
    }
}