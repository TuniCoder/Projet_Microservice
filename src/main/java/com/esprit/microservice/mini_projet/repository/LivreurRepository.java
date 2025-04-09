package com.esprit.microservice.mini_projet.repository;

import com.esprit.microservice.mini_projet.models.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Integer> {
}