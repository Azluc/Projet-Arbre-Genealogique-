package com.cytech.classeTestsUnitaires;

import java.util.*;

import com.cytech.classeProjet.Utilisateur;

public class TestUtilisateur{

    public static void main(String[] args) {
        testConstructeurUtilisateur();

    }

    public static void testConstructeurUtilisateur() {
        // Données de test
        String nom = "Dupont";
        String prenom = "Jean";
        String nationalite = "Française";

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date dateNaissance = cal.getTime();

        Date dateDeces = null; // Utilisateur vivant, donc date décès nulle

        Double codePublic = 12345.0;
        Double codePrive = 67890.0;
        String email = "jean.dupont@example.com";
        String motDePasse = "motdepasse123";
        Double numeroSecuriteSociale = 1234567890123.0;
        String telephone = "0601020304";
        String adresse = "123 rue de Paris, 75000 Paris";

        // Création d’un Utilisateur
        Utilisateur utilisateur = new Utilisateur(
                nom, prenom, nationalite, dateNaissance, dateDeces,
                codePublic, codePrive, email, motDePasse,
                numeroSecuriteSociale, telephone, adresse
        );

        System.out.println("Nom attendu: " + nom + ", Nom obtenu: " + utilisateur.getNom());
        if (nom.equals(utilisateur.getNom())) {
            System.out.println("Nom : OK");
        } else {
            System.out.println("Nom : Échec");
        }

        System.out.println("Prénom attendu: " + prenom + ", Prénom obtenu: " + utilisateur.getPrenom());
        if (prenom.equals(utilisateur.getPrenom())) {
            System.out.println("Prénom : OK");
        } else {
            System.out.println("Prénom : Échec");
        }

        System.out.println("Nationalité attendue: " + nationalite + ", Nationalité obtenue: " + utilisateur.getNationalite());
        if (nationalite.equals(utilisateur.getNationalite())) {
            System.out.println("Nationalité : OK");
        } else {
            System.out.println("Nationalité : Échec");
        }

        System.out.println("Date de naissance attendue: " + dateNaissance + ", Date obtenue: " + utilisateur.getDateNaissance());
        if (dateNaissance.equals(utilisateur.getDateNaissance())) {
            System.out.println("Date de naissance : OK");
        } else {
            System.out.println("Date de naissance : Échec");
        }

        System.out.println("Date de décès attendue: null, Date obtenue: " + utilisateur.getDateDeces());
        if (utilisateur.getDateDeces() == null) {
            System.out.println("Date de décès : OK");
        } else {
            System.out.println("Date de décès : Échec");
        }

        System.out.println("Code public attendu: " + codePublic + ", Code obtenu: " + utilisateur.getCodePublic());
        if (codePublic.equals(utilisateur.getCodePublic())) {
            System.out.println("Code public : OK");
        } else {
            System.out.println("Code public : Échec");
        }

        System.out.println("Code privé attendu: " + codePrive + ", Code obtenu: " + utilisateur.getCodePrive());
        if (codePrive.equals(utilisateur.getCodePrive())) {
            System.out.println("Code privé : OK");
        } else {
            System.out.println("Code privé : Échec");
        }

        System.out.println("Email attendu: " + email + ", Email obtenu: " + utilisateur.getEmail());
        if (email.equals(utilisateur.getEmail())) {
            System.out.println("Email : OK");
        } else {
            System.out.println("Email : Échec");
        }

        System.out.println("Mot de passe attendu: " + motDePasse + ", Mot de passe obtenu: " + utilisateur.getMotDePasse());
        if (motDePasse.equals(utilisateur.getMotDePasse())) {
            System.out.println("Mot de passe : OK");
        } else {
            System.out.println("Mot de passe : Échec");
        }

        System.out.println("Numéro sécurité sociale attendu: " + numeroSecuriteSociale + ", Numéro obtenu: " + utilisateur.getNumeroSecuriteSociale());
        if (numeroSecuriteSociale.equals(utilisateur.getNumeroSecuriteSociale())) {
            System.out.println("Numéro sécurité sociale : OK");
        } else {
            System.out.println("Numéro sécurité sociale : Échec");
        }

        System.out.println("Téléphone attendu: " + telephone + ", Téléphone obtenu: " + utilisateur.getTelephone());
        if (telephone.equals(utilisateur.getTelephone())) {
            System.out.println("Téléphone : OK");
        } else {
            System.out.println("Téléphone : Échec");
        }

        System.out.println("Adresse attendue: " + adresse + ", Adresse obtenue: " + utilisateur.getAdresse());
        if (adresse.equals(utilisateur.getAdresse())) {
            System.out.println("Adresse : OK");
        } else {
            System.out.println("Adresse : Échec");
        }
    }




}

