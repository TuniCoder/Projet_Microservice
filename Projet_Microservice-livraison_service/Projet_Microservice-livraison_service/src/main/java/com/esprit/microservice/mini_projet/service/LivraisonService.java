package com.esprit.microservice.mini_projet.service;

import com.esprit.microservice.mini_projet.models.Livraison;
import com.esprit.microservice.mini_projet.models.Livreur;
import com.esprit.microservice.mini_projet.repository.LivraisonRepo;
import com.esprit.microservice.mini_projet.repository.LivreurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivraisonService {

    @Autowired
    private LivreurRepo livreurRepo;

    @Autowired
    private LivraisonRepo livraisonRepo;

    public List<Livraison> getAllLivraisons() {
        return livraisonRepo.findAll();
    }

    public Optional<Livraison> getLivraisonById(Integer id) {
        return livraisonRepo.findById(id);
    }

    public Livraison createLivraison(Livraison livraison) {
        Livreur livreur = livraison.getLivreur();
        if (livreur.getId() == null) {
            livreur = livreurRepo.save(livreur);
        }
        livraison.setLivreur(livreur);
        livraisonRepo.save(livraison);
        return livraison;
    }

    public Optional<Livraison> updateLivraison(int id, Livraison livraisonDetails) {
        return livraisonRepo.findById(id).map(livraison -> {
            livraison.setDateCreation(livraisonDetails.getDateCreation());
            livraison.setDateLivree(livraisonDetails.getDateLivree());
            livraison.setStatusLivraison(livraisonDetails.getStatusLivraison());
            livraison.setLivreur(livraisonDetails.getLivreur());
            return livraisonRepo.save(livraison);
        });
    }

    public boolean deleteLivraison(Integer id) {
        return livraisonRepo.findById(id).map(livraison -> {
            livraisonRepo.delete(livraison);
            return true;
        }).orElse(false);
    }
}