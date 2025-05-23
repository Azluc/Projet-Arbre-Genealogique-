package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Utilisateur;

/**
 * Test class for Utilisateur functionality.
 * This class contains unit tests for the Utilisateur class,
 * including tests for constructor, getters, membership status,
 * password modification, and string representation.
 */
public class TestUtilisateur {
    /**
     * Main method to run all tests for Utilisateur.
     * Creates a test user and verifies various aspects of the user object:
     * - Constructor and getter methods
     * - Default membership status
     * - Membership validation
     * - Password modification
     * - String representation
     */
    public static void main(String[] args) {

        Utilisateur u = new Utilisateur("Dupont", "Marie", "marie@exemple.com", "mdp123");

        // Test 1 : Vérification des getters
        if (u.getNom().equals("Dupont") && u.getPrenom().equals("Marie") &&
            u.getEmail().equals("marie@exemple.com") && u.getMotDePasse().equals("mdp123")) {
            System.out.println("Test 1 - Constructeur et getters : OK");
        } else {
            System.out.println("Test 1 - Constructeur et getters : ÉCHEC");
        }

        // Test 2 : État d'adhésion par défaut
        if (!u.isAdhesionValidee()) {
            System.out.println("Test 2 - Adhésion par défaut (false) : OK");
        } else {
            System.out.println("Test 2 - Adhésion par défaut (false) : ÉCHEC");
        }

        // Test 3 : Validation de l'adhésion
        u.setAdhesionValidee(true);
        if (u.isAdhesionValidee()) {
            System.out.println("Test 3 - Validation adhésion : OK");
        } else {
            System.out.println("Test 3 - Validation adhésion : ÉCHEC");
        }

        // Test 4 : Modification du mot de passe
        u.setMotDePasse("newpass");
        if (u.getMotDePasse().equals("newpass")) {
            System.out.println("Test 4 - Changement mot de passe : OK");
        } else {
            System.out.println("Test 4 - Changement mot de passe : ÉCHEC");
        }

        // Test 5 : Affichage toString
        if (u.toString().contains("Marie Dupont") && u.toString().contains("marie@exemple.com")) {
            System.out.println("Test 5 - toString utilisateur : OK");
        } else {
            System.out.println("Test 5 - toString utilisateur : ÉCHEC");
        }
    }
}
