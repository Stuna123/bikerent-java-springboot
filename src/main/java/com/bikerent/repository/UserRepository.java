// Repository qui permet d'accéder aux utilisateurs en base de données
package com.bikerent.repository;

import com.bikerent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par email
    Optional<User> findByEmail(String email);

    // Vérifie si un utilisateur existe déjà avec cet email
    boolean existsByEmail(String email);
}