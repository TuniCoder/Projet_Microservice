package com.esprit.microservice.recllamation.ReclamationRepository;

import com.esprit.microservice.recllamation.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepo extends JpaRepository<Reclamation, Long>   {
}
