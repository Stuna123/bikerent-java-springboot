package com.bikerent.service;

import com.bikerent.entity.Bike;
import com.bikerent.repository.BikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service indique que la classe contient la logique metier
// ou encapsuler la logique metier
@Service

// BikeService est un intermediaire entre le controller et le repository
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    // Cette méthode retourne tous les vélos
    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    // Cette méthode retourne un vélo selon son id
    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found"));
    }

    // Cette méthode filtre les vélos par type
    public List<Bike> getBikesByType(String type) {
        if (type == null || type.isBlank()) {
            return bikeRepository.findAll();
        }

        return bikeRepository.findByTypeIgnoreCase(type);
    }
}
