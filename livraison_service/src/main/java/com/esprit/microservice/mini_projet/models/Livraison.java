package com.esprit.microservice.mini_projet.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class Livraison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date DateCreation, DateLivree;
    private String StatusLivraison;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        DateCreation = dateCreation;
    }

    public Date getDateLivree() {
        return DateLivree;
    }

    public void setDateLivree(Date dateLivree) {
        DateLivree = dateLivree;
    }

    public String getStatusLivraison() {
        return StatusLivraison;
    }

    public void setStatusLivraison(String statusLivraison) {
        StatusLivraison = statusLivraison;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    @ManyToOne
    @JoinColumn(name = "idLivreur")
    private Livreur livreur;

    public Livraison(Date dateCreation, Date dateLivree, String statusLivraison, Livreur livreur) {
        DateCreation = dateCreation;
        DateLivree = dateLivree;
        StatusLivraison = statusLivraison;
        this.livreur = livreur;
    }

    public Livraison() {
    }
}