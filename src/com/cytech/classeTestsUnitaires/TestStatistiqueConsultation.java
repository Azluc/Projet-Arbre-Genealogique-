package com.cytech.classeProjet;

public class TestStatistiqueConsultation_Console {
    public static void main(String[] args) {

        Utilisateur utilisateur = new Utilisateur("Test", "User", "test@mail.com", "mdp123");

        StatistiqueConsultation stat = new StatistiqueConsultation(3.0, utilisateur);

        // Test 1 : Nombre de consultations initial
        if (stat.getNombreConsultation() == 3.0) {
            System.out.println("Test 1 - Nombre de consultations initial : OK");
        } else {
            System.out.println("Test 1 - Nombre de consultations initial : ÉCHEC");
        }


        if (stat.getConsultant() == utilisateur) {
            System.out.println("Test 2 - Consultant associé : OK");
        } else {
            System.out.println("Test 2 - Consultant associé : ÉCHEC");
        }

 
        stat.augmenterConsultation(2.0);
        if (stat.getNombreConsultation() == 5.0) {
            System.out.println("Test 3 - Augmenter consultation : OK");
        } else {
            System.out.println("Test 3 - Augmenter consultation : ÉCHEC");
        }


        if (stat.toString().contains("5.0") || stat.toString().contains(utilisateur.getNom())) {
            System.out.println("Test 4 - toString() : OK");
        } else {
            System.out.println("Test 4 - toString() : ÉCHEC (ou non redéfini)");
        }
    }
}
