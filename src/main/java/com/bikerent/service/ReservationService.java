// Service qui contient la logique métier des réservations
package com.bikerent.service;

import com.bikerent.entity.Bike;
import com.bikerent.entity.Reservation;
import com.bikerent.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PricingService pricingService;

    // Injection des dépendances nécessaires
    public ReservationService(ReservationRepository reservationRepository, PricingService pricingService) {
        this.reservationRepository = reservationRepository;
        this.pricingService = pricingService;
    }

    // Valide les données métier puis sauvegarde la réservation
    public Reservation saveReservation(Reservation reservation, Bike bike) {

        // Vérifie que la date de début n'est pas après la date de fin
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            throw new RuntimeException("La date de début ne peut pas être après la date de fin.");
        }

        // Pour cette version, on impose une réservation sur une seule journée
        if (!reservation.getStartDate().isEqual(reservation.getEndDate())) {
            throw new RuntimeException("Pour le moment, la réservation doit être effectuée sur une seule journée.");
        }

        // Si c'est le même jour, l'heure de début doit être inférieure à l'heure de fin
        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new RuntimeException("Pour une réservation sur la même journée, l'heure de début doit être inférieure à l'heure de fin.");
        }

        // Vérifie si la même réservation existe déjà pour ce vélo
        // Vérifie les conflits réels de réservation (chevauchement horaire)
        if (reservationRepository.existsConflict(
                bike.getId(),
                reservation.getStartDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        )) {
            throw new RuntimeException(
                    "Ce vélo est déjà réservé sur ce créneau horaire !"
            );
        }

        // Calcule automatiquement le prix total
        BigDecimal totalPrice = pricingService.calculatePrice(
                reservation.getStartTime(),
                reservation.getEndTime()
        );

        // Associe le vélo à la réservation
        reservation.setBike(bike);

        // Stocke le prix calculé
        reservation.setTotalPrice(totalPrice);

        // Sauvegarde la réservation
        return reservationRepository.save(reservation);
    }

    // Retourne toutes les réservations triées pour l'historique
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllByOrderByStartDateDescStartTimeDesc();
    }

    // Retourne uniquement les réservations d'un utilisateur à partir de son email
    public List<Reservation> getReservationsByUserEmail(String email) {
        return reservationRepository.findByUserEmailOrderByStartDateDescStartTimeDesc(email);
    }
}