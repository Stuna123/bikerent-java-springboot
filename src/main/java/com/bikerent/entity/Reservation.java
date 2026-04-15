// Cette classe représente une réservation de vélo
package com.bikerent.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Statut actuel de la réservation
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // Date de début de réservation
    private LocalDate startDate;

    // Date de fin de réservation
    private LocalDate endDate;

    // Heure de début de réservation
    private LocalTime startTime;

    // Heure de fin de réservation
    private LocalTime endTime;

    // Prix total calculé automatiquement
    private BigDecimal totalPrice;

    // Relation : plusieurs réservations peuvent concerner un même vélo
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    // Relation : plusieurs réservations peuvent appartenir à un même utilisateur
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructeur
    public Reservation() {
    }

    public Reservation(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, BigDecimal totalPrice, Bike bike) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.bike = bike;
    }

    // Getter et setter
    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}