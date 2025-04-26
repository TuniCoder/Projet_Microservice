package com.esprit.microservice.mini_projet.controller;

import com.esprit.microservice.mini_projet.models.Livraison;
import com.esprit.microservice.mini_projet.service.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = "*")
@RestController
@RequestMapping("/api/livraisons")
public class LivraisonRestAPI {

    @Autowired
    public LivraisonService livraisonService;

    // ✅ Combined pagination and non-paginated version
    @GetMapping
    public ResponseEntity<?> getAllLivraisons(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

            List<Livraison> result = livraisonService.getAllLivraisons();
            return ResponseEntity.ok(result);
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

    @PutMapping("/{livraisonId}/assign/{livreurId}")
    public ResponseEntity<Livraison> assignLivreurToLivraison(
            @PathVariable Integer livraisonId,
            @PathVariable Integer livreurId) {
        Optional<Livraison> updated = livraisonService.assignLivreurToLivraison(livraisonId, livreurId);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/export/csv")
    public ResponseEntity<InputStreamResource> exportLivraisonsCsv(
            @RequestParam(required = false) Integer livreurId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {

        ByteArrayInputStream in = livraisonService.exportLivraisonsToCsv(livreurId, start, end);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=livraisons.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<InputStreamResource> exportLivraisonsPdf(
            @RequestParam(required = false) Integer livreurId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {

        ByteArrayInputStream pdfStream = livraisonService.exportLivraisonsToPdf(livreurId, start, end);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=livraisons.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
    @PutMapping("/{id}/mark-as-livree")
    public ResponseEntity<Livraison> markLivraisonAsLivree(@PathVariable Integer id) {
        Optional<Livraison> updated = livraisonService.markAsLivree(id);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
