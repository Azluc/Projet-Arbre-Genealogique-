package com.cytech.classeProjet;

public class Administrateur extends Utilisateur {


    public Administrateur(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }


    public void validerAdhesion(Utilisateur utilisateur) {
        utilisateur.setAdhesionValidee(true);
        System.out.println("Adhésion validée pour : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    }


    public void refuserAdhesion(Utilisateur utilisateur) {
        utilisateur.setAdhesionValidee(false);
        System.out.println("Adhésion refusée pour : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    }
}
