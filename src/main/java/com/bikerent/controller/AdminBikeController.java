package com.bikerent.controller;

import com.bikerent.repository.BikeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bikes")

public class AdminBikeController {
    private final BikeRepository bikeRepository;

    public AdminBikeController(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @GetMapping
    public String listBikes(Model model) {
        model.addAttribute("bikes", bikeRepository.findAll());
        return "admin/bikes";
    }
}
