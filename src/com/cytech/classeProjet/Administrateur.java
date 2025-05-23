package com.cytech.classeProjet;

/**
 * Class representing an administrator of the genealogical tree application.
 * An administrator inherits the basic functionality of a user and has
 * additional rights to manage user memberships.
 */
public class Administrateur extends Utilisateur {

    /**
     * Constructor for the Administrator class.
     * 
     * @param nom The administrator's last name
     * @param prenom The administrator's first name
     * @param email The administrator's email address
     * @param motDePasse The administrator's password
     */
    public Administrateur(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    /**
     * Validates a user's membership.
     * This method activates the user's account by setting their membership as validated.
     * 
     * @param utilisateur The user whose membership should be validated
     */
    public void validerAdhesion(Utilisateur utilisateur) {
        utilisateur.setAdhesionValidee(true);
        System.out.println("Membership validated for: " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    }

    /**
     * Rejects a user's membership.
     * This method deactivates the user's account by setting their membership as not validated.
     * 
     * @param utilisateur The user whose membership should be rejected
     */
    public void refuserAdhesion(Utilisateur utilisateur) {
        utilisateur.setAdhesionValidee(false);
        System.out.println("Membership rejected for: " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    }
}
