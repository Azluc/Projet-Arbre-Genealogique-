package com.cytech.classeProjet;

public class Utilisateur {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private boolean adhesionValidee;

    public Utilisateur(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adhesionValidee = false; // par défaut
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean isAdhesionValidee() {
        return adhesionValidee;
    }

    // Setters
    public void setAdhesionValidee(boolean adhesionValidee) {
        this.adhesionValidee = adhesionValidee;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " - " + email + (adhesionValidee ? " (validé)" : " (en attente)");
    }
}
