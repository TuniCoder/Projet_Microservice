package com.esprit.microservice.mini_projet.dto;

import com.esprit.microservice.mini_projet.models.Livraison;

import java.sql.Date;

public class LivraisonDTO {

    private Integer id;
    private Date dateCreation;
    private Date dateLivree;
    private String statusLivraison;
    private String livreurNom;

    public LivraisonDTO(Livraison livraison) {
        this.id = livraison.getId();
        this.dateCreation = livraison.getDateCreation();
        this.dateLivree = livraison.getDateLivree();
        this.statusLivraison = livraison.getStatusLivraison();
        this.livreurNom = livraison.getLivreur() != null ? livraison.getLivreur().getNom() : "Non attribué";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLivree() {
        return dateLivree;
    }

    public void setDateLivree(Date dateLivree) {
        this.dateLivree = dateLivree;
    }

    public String getStatusLivraison() {
        return statusLivraison;
    }

    public void setStatusLivraison(String statusLivraison) {
        this.statusLivraison = statusLivraison;
    }

    public String getLivreurNom() {
        return livreurNom;
    }

    public void setLivreurNom(String livreurNom) {
        this.livreurNom = livreurNom;
    }

}
