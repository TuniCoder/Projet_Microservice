package com.esprit.microservice.mini_projet.controller;

import com.esprit.microservice.mini_projet.models.Livraison;
import com.esprit.microservice.mini_projet.service.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esprit.microservice.mini_projet.dto.LivraisonDTO;


import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/livraisons")
public class LivraisonRestAPI {

    @Autowired
    public LivraisonService livraisonService;

    @GetMapping
    public List<LivraisonDTO> getAllLivraisons() {
        List<Livraison> livraisons = livraisonService.getAllLivraisons();
        return livraisons.stream().map(LivraisonDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Integer id) {
        Optional<Livraison> livraison = livraisonService.getLivraisonById(id);
        return livraison.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Livraison createLivraison(@RequestBody Livraison livraison) {
        return livraisonService.createLivraison(livraison);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Livraison> updateLivraison(@PathVariable Integer id, @RequestBody Livraison livraisonDetails) {
        Optional<Livraison> updatedLivraison = livraisonService.updateLivraison(id, livraisonDetails);
        return updatedLivraison.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Integer id) {
        if (livraisonService.deleteLivraison(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}