package com.esprit.microservice.mini_projet.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class Livraison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_creation")
    private Date dateCreation;

    @Column(name = "date_livree")
    private Date dateLivree;

    @Column(name = "status_livraison")
    private String statusLivraison;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;

    public Livraison() {}

    public Livraison(Date dateCreation, Date dateLivree, String statusLivraison, Livreur livreur) {
        this.dateCreation = dateCreation;
        this.dateLivree = dateLivree;
        this.statusLivraison = statusLivraison;
        this.livreur = livreur;
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

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

}
