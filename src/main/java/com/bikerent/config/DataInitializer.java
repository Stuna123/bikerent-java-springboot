package com.bikerent.config;

import com.bikerent.entity.Bike;
import com.bikerent.repository.BikeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initBikes(BikeRepository bikeRepository) {
        return args -> {

            // Debug : affiche le nombre de vélos trouvés au démarrage
            long bikeCount = bikeRepository.count();
            // System.out.println("===== DEBUG DATA INITIALIZER =====");
            // System.out.println("Nombre de vélos trouvés en base au démarrage : " + bikeCount);

            // On ajoute les vélos seulement si la table est vide
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
                        .description("Rapide, pratique et idéal pour les longues distances")
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

                System.out.println("Insertion terminée.");
            } else {
                System.out.println("Insertion ignorée car la table bike n'est pas vide.");
            }

        };
    }
}