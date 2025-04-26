package com.esprit.microservice.mini_projet.repository;

import com.esprit.microservice.mini_projet.models.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LivraisonRepo extends JpaRepository<Livraison, Integer> {

    // Find deliveries for a specific livreur within a date range
    List<Livraison> findByLivreur_IdAndDateCreationBetween(Integer livreurId, Date startDate, Date endDate);

    // Find deliveries within a date range
    List<Livraison> findByDateCreationBetween(Date start, Date end);
}
