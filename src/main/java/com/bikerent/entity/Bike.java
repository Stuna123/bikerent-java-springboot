package com.bikerent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Bike {
    // clé primaire
    @Id

    // Auto-incrémentation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name, type;
    private BigDecimal hourlyPrice;
    private String description, imageName;
    private boolean available;
}