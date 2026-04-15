// Service qui centralise les données utiles au dashboard administrateur
package com.bikerent.service;

import com.bikerent.repository.BikeRepository;
import com.bikerent.repository.ReservationRepository;
import com.bikerent.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BikeRepository bikeRepository;

    // Injection des repositories nécessaires pour construire les statistiques admin
    public AdminService(UserRepository userRepository,
                        ReservationRepository reservationRepository,
                        BikeRepository bikeRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.bikeRepository = bikeRepository;
    }

    // Retourne le nombre total d'utilisateurs
    public long getTotalUsers() {
        return userRepository.count();
    }

    // Retourne le nombre total de réservations
    public long getTotalReservations() {
        return reservationRepository.count();
    }

    // Retourne le nombre total de vélos
    public long getTotalBikes() {
        return bikeRepository.count();
    }

    // Calcule le chiffre d'affaires total à partir des réservations
    public BigDecimal getTotalRevenue() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> reservation.getTotalPrice() != null ? reservation.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}