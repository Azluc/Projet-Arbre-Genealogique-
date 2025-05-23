package com.cytech.classeProjet;

/**
 * Class representing a user of the genealogical tree application.
 * A user has personal information and a membership status.
 */
public class Utilisateur {

    /** The user's last name */
    private String nom;
    
    /** The user's first name */
    private String prenom;
    
    /** The user's email address */
    private String email;
    
    /** The user's password */
    private String motDePasse;
    
    /** The user's membership validation status */
    private boolean adhesionValidee;

    /**
     * Constructor for the User class.
     * 
     * @param nom The user's last name
     * @param prenom The user's first name
     * @param email The user's email address
     * @param motDePasse The user's password
     */
    public Utilisateur(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adhesionValidee = false; // by default
    }

    /**
     * Returns the user's last name.
     * 
     * @return The last name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Returns the user's first name.
     * 
     * @return The first name
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Returns the user's email address.
     * 
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's password.
     * 
     * @return The password
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Checks if the user's membership is validated.
     * 
     * @return true if the membership is validated, false otherwise
     */
    public boolean isAdhesionValidee() {
        return adhesionValidee;
    }

    /**
     * Sets the user's membership validation status.
     * 
     * @param adhesionValidee The new validation status
     */
    public void setAdhesionValidee(boolean adhesionValidee) {
        this.adhesionValidee = adhesionValidee;
    }

    /**
     * Changes the user's password.
     * 
     * @param motDePasse The new password
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Returns a text representation of the user.
     * Format: "first name last name - email (validated/pending)"
     * 
     * @return The text representation of the user
     */
    @Override
    public String toString() {
        return prenom + " " + nom + " - " + email + (adhesionValidee ? " (validated)" : " (pending)");
    }
}
