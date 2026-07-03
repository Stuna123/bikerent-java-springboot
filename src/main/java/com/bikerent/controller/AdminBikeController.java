package com.bikerent.controller;

import com.bikerent.entity.Bike;
import com.bikerent.repository.BikeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bikes")
public class AdminBikeController {

    private final BikeRepository bikeRepository;

    public AdminBikeController(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    // Read - afficher tous les vélos
    @GetMapping
    public String listBikes(Model model) {
        model.addAttribute("bikes", bikeRepository.findAll());
        return "admin/bikes";
    }

    // Create - afficher le formulaire d'ajout
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bike", new Bike());
        return "admin/bike-form";
    }

    // Create/Update - enregistrer un vélo
    @PostMapping
    public String saveBike(@ModelAttribute Bike bike) {
        bikeRepository.save(bike);
        return "redirect:/admin/bikes";
    }

    // Update - afficher le formulaire
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Velo introuvable avec l'id : " + id));

        model.addAttribute("bike", bike);
        model.addAttribute("pageTitle", "Modifier un vélo");
        return "admin/bike-form";
    }

    // Delete - supprimer un vélo
    @GetMapping("/delete/{id}")
    public String deleteBike(@PathVariable Long id) {
        if(!bikeRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : le vélo est introuvable avec l'id : " + id);
        }

        bikeRepository.deleteById(id);
        return "redirect:/admin/bikes";
    }

}
