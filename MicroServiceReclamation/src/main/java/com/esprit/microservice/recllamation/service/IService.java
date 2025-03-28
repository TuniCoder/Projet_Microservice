package com.esprit.microservice.recllamation.service;

import com.esprit.microservice.recllamation.entity.Reclamation;
import java.util.List;

public interface IService {
    Reclamation addReclamation(Reclamation reclamation);
    Reclamation updateReclamation(Long id, Reclamation reclamation);
    void deleteReclamation(Long id);
    Reclamation getReclamationById(Long id);
    List<Reclamation> getAllReclamations();
}