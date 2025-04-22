package com.esprit.microservice.recllamation.ReclamationRepository;

import com.esprit.microservice.recllamation.entity.Reclamation;
import com.esprit.microservice.recllamation.entity.TypeReclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepo extends JpaRepository<Reclamation, Long>   {
    List<Reclamation> findAllByOrderByDateCreationDesc();
    List<Reclamation> findByType(TypeReclamation type); // New method to filter by type
}
