package com.esprit.microservice.mini_projet.service;

import com.esprit.microservice.mini_projet.models.Livreur;
import com.esprit.microservice.mini_projet.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreurService {

    @Autowired
    private LivreurRepository livreurRepo;

    public List<Livreur> getAllLivreurs() {
        return livreurRepo.findAll();
    }

    public Optional<Livreur> getLivreurById(Integer id) {
        return livreurRepo.findById(id);
    }

    public Livreur createLivreur(Livreur livreur) {
        return livreurRepo.save(livreur);
    }

    public Optional<Livreur> updateLivreur(int id, Livreur livreurDetails) {
        return livreurRepo.findById(id).map(livreur -> {
            livreur.setNom(livreurDetails.getNom());
            livreur.setPrenom(livreurDetails.getPrenom());
            livreur.setAddresse(livreurDetails.getAddresse());
            livreur.setMobile(livreurDetails.getMobile());
            livreur.setLivraisons(livreurDetails.getLivraisons());
            return livreurRepo.save(livreur);
        });
    }

    public boolean deleteLivreur(Integer id) {
        return livreurRepo.findById(id).map(livreur -> {
            livreurRepo.delete(livreur);
            return true;
        }).orElse(false);
    }
}