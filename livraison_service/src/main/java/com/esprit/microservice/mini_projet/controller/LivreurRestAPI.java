package com.esprit.microservice.mini_projet.controller;

import com.esprit.microservice.mini_projet.models.Livreur;
import com.esprit.microservice.mini_projet.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = "*")
@RestController
@RequestMapping("/api/livreurs")
public class LivreurRestAPI {

    @Autowired
    private LivreurService livreurService;

    // ✅ Combined non-paginated and paginated in one method
    @GetMapping
    public ResponseEntity<?> getAllLivreurs(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && size != null) {
            Page<Livreur> result = livreurService.getAllLivreurs(PageRequest.of(page, size));
            return ResponseEntity.ok(result);
        } else {
            List<Livreur> result = livreurService.getAllLivreurs();
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Integer id) {
        Optional<Livreur> livreur = livreurService.getLivreurById(id);
        return livreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Livreur createLivreur(@RequestBody Livreur livreur) {
        return livreurService.createLivreur(livreur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livreur> updateLivreur(@PathVariable Integer id, @RequestBody Livreur livreurDetails) {
        Optional<Livreur> updatedLivreur = livreurService.updateLivreur(id, livreurDetails);
        return updatedLivreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Integer id) {
        if (livreurService.deleteLivreur(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
