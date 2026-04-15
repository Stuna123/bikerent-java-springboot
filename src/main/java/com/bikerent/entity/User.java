// Cette classe représente un utilisateur de l'application
package com.bikerent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom complet ou prénom de l'utilisateur
    private String name;

    // Email unique utilisé pour la connexion
    @Column(unique = true, nullable = false)
    private String email;

    // Mot de passe encodé
    @Column(nullable = false)
    private String password;

    // Rôle de l'utilisateur (USER ou ADMIN)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Constructeur
    public User() {
    }

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Get & Set
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}