package com.bikerent.controller;

import com.bikerent.service.BikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    // Cette méthode affiche la liste des vélos avec filtre optionnel par type
    @GetMapping("/bikes")
    public String listBikes(@RequestParam(required = false) String type, Model model) {
        model.addAttribute("bikes", bikeService.getBikesByType(type));
        model.addAttribute("selectedType", type);
        return "bikes";
    }

    // Cette méthode affiche le détail d'un vélo en se basant sur son id
    @GetMapping("/bikes/{id}")
    public String bikeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("bike",bikeService.getBikeById(id));
        return "bike-details";
    }
}
