package com.cytech.classeProjet;

public class TestAdministrateur {
    public static void main(String[] args) {
        Administrateur admin = new Administrateur("Admin", "Test", "admin@mail.com", "admin123");
        Utilisateur utilisateur = new Utilisateur("Doe", "John", "john@mail.com", "pass123");

        // Test 1 : Vérification du constructeur
        if (admin.getNom().equals("Admin") && admin.getPrenom().equals("Test") && admin.getEmail().equals("admin@mail.com")) {
            System.out.println("Test 1 - Constructeur administrateur : OK");
        } else {
            System.out.println("Test 1 - Constructeur administrateur : ÉCHEC");
        }

        // Test 2 : Valider adhésion
        admin.validerAdhesion(utilisateur);
        if (utilisateur.isAdhesionValidee()) {
            System.out.println("Test 2 - Validation adhésion : OK");
        } else {
            System.out.println("Test 2 - Validation adhésion : ÉCHEC");
        }

        // Test 3 : Refuser adhésion
        admin.refuserAdhesion(utilisateur);
        if (!utilisateur.isAdhesionValidee()) {
            System.out.println("Test 3 - Refus adhésion : OK");
        } else {
            System.out.println("Test 3 - Refus adhésion : ÉCHEC");
        }
    }
}
