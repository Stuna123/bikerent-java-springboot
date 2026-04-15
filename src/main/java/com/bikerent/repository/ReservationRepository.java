// Repository qui permet d'interagir avec la base de données pour les réservations
package com.bikerent.repository;

import com.bikerent.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Vérifie si une réservation existe EXACTEMENT identique
    boolean existsByBikeIdAndStartDateAndEndDateAndStartTimeAndEndTime(
            Long bikeId,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime
    );

    // 🔥 Vérifie s'il existe un conflit de réservation (overlap)
    @Query("""
        SELECT COUNT(r) > 0 FROM Reservation r
        WHERE r.bike.id = :bikeId
        AND r.startDate = :date
        AND (
            r.startTime < :endTime AND r.endTime > :startTime
        )
    """)
    boolean existsConflict(
            Long bikeId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

    // Récupère toutes les réservations triées de la plus récente à la plus ancienne
    List<Reservation> findAllByOrderByStartDateDescStartTimeDesc();

    // Retourne toutes les réservations d'un utilisateur triées de la plus récente à la plus ancienne
    List<Reservation> findByUserEmailOrderByStartDateDescStartTimeDesc(String email);
}