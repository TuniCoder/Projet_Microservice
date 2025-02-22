package com.esprit.microservice.mini_projet.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Livreur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLivreur;

    private String nom;
    private String prenom;
    private String addresse;
    private Integer mobile;

    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL)
    private List<Livraison> livraisons;

    public Livreur() {
    }

    public Livreur(String nom, String prenom, String addresse, Integer mobile, List<Livraison> livraisons) {
        this.nom = nom;
        this.prenom = prenom;
        this.addresse = addresse;
        this.mobile = mobile;
        this.livraisons = livraisons;
    }

    // Getters and Setters
    public Integer getId() {
        return idLivreur;
    }

    public void setId(Integer id) {
        this.idLivreur = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }
}