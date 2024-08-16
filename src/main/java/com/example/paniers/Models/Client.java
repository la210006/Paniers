package com.example.paniers.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "client_id", "prenom", "nom", "commune", "reservations" })
public class Client {
    @JsonProperty("client_id")
    private Long id;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("commune")
    private String commune;

    @JsonProperty("reservations")
    @JsonManagedReference
    private List<Reservation> reservations;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
