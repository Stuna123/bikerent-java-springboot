// Service qui gère la logique métier liée aux utilisateurs
package com.bikerent.service;

import com.bikerent.entity.Role;
import com.bikerent.entity.User;
import com.bikerent.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Injection des dépendances nécessaires
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Inscrit un nouvel utilisateur avec rôle USER et mot de passe encodé
    public void registerUser(User user) {

        // Vérifie si l'email existe déjà
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un compte existe déjà avec cet email.");
        }

        // Encode le mot de passe avant sauvegarde
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Attribue le rôle USER par défaut
        user.setRole(Role.USER);

        // Sauvegarde l'utilisateur
        userRepository.save(user);
    }
}