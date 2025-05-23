package com.cytech.classeTestsUnitaires;

import com.cytech.classeProjet.Administrateur;
import com.cytech.classeProjet.Utilisateur;

/**
 * Test class for Administrateur functionality.
 * This class contains unit tests for the Administrateur class,
 * including tests for constructor, membership validation, and membership rejection.
 */
public class TestAdministrateur {
    /**
     * Main method to run all tests for Administrateur.
     * Creates an administrator and a user, then tests various administrator functions.
     */
    public static void main(String[] args) {
        Administrateur admin = new Administrateur("Admin", "Test", "admin@mail.com", "admin123");
        Utilisateur utilisateur = new Utilisateur("Doe", "John", "john@mail.com", "pass123");

        // Test 1: Constructor verification
        if (admin.getNom().equals("Admin") && admin.getPrenom().equals("Test") && admin.getEmail().equals("admin@mail.com")) {
            System.out.println("Test 1 - Administrator constructor: OK");
        } else {
            System.out.println("Test 1 - Administrator constructor: FAILED");
        }

        // Test 2: Validate membership
        admin.validerAdhesion(utilisateur);
        if (utilisateur.isAdhesionValidee()) {
            System.out.println("Test 2 - Membership validation: OK");
        } else {
            System.out.println("Test 2 - Membership validation: FAILED");
        }

        // Test 3: Reject membership
        admin.refuserAdhesion(utilisateur);
        if (!utilisateur.isAdhesionValidee()) {
            System.out.println("Test 3 - Membership rejection: OK");
        } else {
            System.out.println("Test 3 - Membership rejection: FAILED");
        }
    }
}
