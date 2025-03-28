package com.esprit.microservice.recllamation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@DynamicUpdate
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING) // Pour stocker l'enum comme une chaîne dans la base de données
    private TypeReclamation type;

    private String user_id; // user_id forgien key
}
