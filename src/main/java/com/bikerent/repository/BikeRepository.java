package com.bikerent.repository;

import com.bikerent.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    // Cette methode permet de récupérer les vélos selon leur type
    List<Bike> findByTypeIgnoreCase(String type);
}
