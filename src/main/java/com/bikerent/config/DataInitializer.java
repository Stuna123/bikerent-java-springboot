package com.bikerent.config;

import com.bikerent.entity.Bike;
import com.bikerent.entity.Role;
import com.bikerent.entity.User;
import com.bikerent.repository.BikeRepository;
import com.bikerent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Value("${app.admin.email:francis.admin@bikerent.com}")
    private String adminEmail;

    @Value("${app.admin.password:Qz7N5ZjUh}")
    private String adminPassword;

    @Value("${app.admin.name:adminBikeRent}")
    private String adminName;

    @Bean
    public CommandLineRunner initData(BikeRepository bikeRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            // =========================
            // 1. Initialisation des vélos
            // =========================
            long bikeCount = bikeRepository.count();

            if (bikeCount == 0) {
                System.out.println("La table bike est vide, insertion des 6 vélos...");

                bikeRepository.save(Bike.builder()
                        .name("Vélo Urbain Classic")
                        .type("Ville")
                        .hourlyPrice(new BigDecimal("5.00"))
                        .description("Un vélo confortable pour les trajets en ville.")
                        .imageName("city-bike.jpg")
                        .available(true)
                        .build());

                bikeRepository.save(Bike.builder()
                        .name("VTT Explorer")
                        .type("VTT")
                        .hourlyPrice(new BigDecimal("7.50"))
                        .description("Parfait pour les chemins difficiles et les balades sportives.")
                        .imageName("mountain-bike.jpg")
                        .available(true)
                        .build());

                bikeRepository.save(Bike.builder()
                        .name("Vélo Électrique Flash")
                        .type("ÉLECTRIQUE")
                        .hourlyPrice(new BigDecimal("10.00"))
                        .description("Rapide, pratique et idéal pour les longues distances.")
                        .imageName("electric-bike.jpg")
                        .available(true)
                        .build());

                bikeRepository.save(Bike.builder()
                        .name("Vélo Pliant Urban Move")
                        .type("Pliant")
                        .hourlyPrice(new BigDecimal("6.50"))
                        .description("Compact et pratique pour les déplacements quotidiens.")
                        .imageName("bike-folding.jpg")
                        .available(true)
                        .build());

                bikeRepository.save(Bike.builder()
                        .name("Vélo Route Speedster")
                        .type("Route")
                        .hourlyPrice(new BigDecimal("8.50"))
                        .description("Léger et rapide pour les sorties sportives sur route.")
                        .imageName("bike-road.jpg")
                        .available(true)
                        .build());

                bikeRepository.save(Bike.builder()
                        .name("Vélo Cargo Family Plus")
                        .type("Cargo")
                        .hourlyPrice(new BigDecimal("9.50"))
                        .description("Idéal pour transporter des courses ou rouler en famille.")
                        .imageName("bike-cargo.jpg")
                        .available(true)
                        .build());

                System.out.println("Insertion des vélos terminée.");
            } else {
                System.out.println("Insertion des vélos ignorée car la table bike n'est pas vide.");
            }

            // =========================
            // 2. Création de l'admin
            // =========================
            boolean adminExists = userRepository.existsByEmail(adminEmail);

            if (!adminExists) {
                User admin = new User();
                admin.setName(adminName);
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin créé avec succès : " + adminEmail);
            } else {
                System.out.println("Admin déjà existant : " + adminEmail);
            }
        };
    }
}