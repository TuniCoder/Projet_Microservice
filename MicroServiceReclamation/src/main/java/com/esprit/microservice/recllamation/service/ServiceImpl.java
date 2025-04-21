package com.esprit.microservice.recllamation.service;

import com.esprit.microservice.recllamation.entity.Reclamation;
import com.esprit.microservice.recllamation.ReclamationRepository.ReclamationRepo;
import com.esprit.microservice.recllamation.entity.TypeReclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements IService {

    @Autowired
    private ReclamationRepo reclamationRepo;

    @Override
    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepo.save(reclamation);
    }

    @Override
    public Reclamation updateReclamation(Long id, Reclamation reclamation) {
        if (reclamationRepo.findById(id).isPresent()) {
            Reclamation existingReclamation = reclamationRepo.findById(id).get();
            existingReclamation.setTitre(reclamation.getTitre());
            existingReclamation.setDescription(reclamation.getDescription());
            existingReclamation.setType(reclamation.getType());
            existingReclamation.setUser_id(reclamation.getUser_id());
            return reclamationRepo.save(existingReclamation);
        } else {
            return null;
        }
    }
    /*@Override
    public Reclamation updateReclamation(Long id, Reclamation reclamation) {
        return reclamationRepo.findById(id).map(existingReclamation -> {
            // Update only the fields that are not null in the incoming reclamation
            if (reclamation.getTitre() != null) {
                existingReclamation.setTitre(reclamation.getTitre());
            }
            if (reclamation.getDescription() != null) {
                existingReclamation.setDescription(reclamation.getDescription());
            }
            if (reclamation.getType() != null) {
                existingReclamation.setType(reclamation.getType());
            }
            if (reclamation.getUser_id() != 0) { // Assuming user_id is an int and 0 is not a valid value
                existingReclamation.setUser_id(reclamation.getUser_id());
            }
            return reclamationRepo.save(existingReclamation);
        }).orElse(null); // Return null if the reclamation with the given ID is not found
    }*/

    @Override
    public void deleteReclamation(Long id) {
        reclamationRepo.deleteById(id);
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        return reclamationRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepo.findAll();
    }

    @Override
    public List<Reclamation> getReclamationsByNewestDate() {
        return reclamationRepo.findAllByOrderByDateCreationDesc();
    }

    @Override
    public List<Reclamation> getReclamationsByType(TypeReclamation type) {
        return reclamationRepo.findByType(type);
    }
}