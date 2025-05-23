package com.cytech.classeTestsUnitaires;

import java.util.Calendar;
import java.util.Date;

import com.cytech.classeProjet.Personne;

/**
 * Test class for Personne functionality.
 * This class contains unit tests for the Personne class,
 * verifying the correct behavior of person creation, date validation,
 * and attribute management.
 */
public class TestPersonne {

    /**
     * Main method to run all tests for Personne.
     * Executes tests for person creation and date validation.
     */
    public static void main(String[] args) {
        testConstructeurPersonne();
        testVerifierCoherenceDates();
    }

    /**
     * Tests the Personne constructor and attribute management.
     * Verifies that a person can be created with valid attributes
     * and that all getters return the correct values.
     */
    public static void testConstructeurPersonne() {

        // Création d'une Personne
        String nom = "nom1";
        String prenom = "prenom1";
        String nationalite = "France";

        Calendar calendrier = Calendar.getInstance();
        calendrier.set(2000, Calendar.JANUARY, 1);  // 1er janvier 2000
        Date dateNaissance = calendrier.getTime();

        Calendar calendrier2 = Calendar.getInstance();
        calendrier2.set(2010, Calendar.MAY, 16);  // 16 Mai 2010
        Date dateDeces = calendrier2.getTime();

        Personne personne = new Personne(nom, prenom, nationalite, dateNaissance, dateDeces);

        // Vérification des attributs
        if (!prenom.equals(personne.getPrenom())) {
            System.out.println("Échec : le prénom attendu est " + prenom + " mais obtenu : " + personne.getPrenom());
        } else {
            System.out.println("Prénom : OK");
        }

        if (!nom.equals(personne.getNom())) {
            System.out.println("Échec : le nom attendu est " + nom + " mais obtenu : " + personne.getNom());
        } else {
            System.out.println("Nom : OK");
        }

        if (!nationalite.equals(personne.getNationalite())) {
            System.out.println("Échec : la nationalité attendue est " + nationalite + " mais obtenue : " + personne.getNationalite());
        } else {
            System.out.println("Nationalité : OK");
        }

        if (!dateNaissance.equals(personne.getDateNaissance())) {
            System.out.println("Échec : la date de naissance attendue est " + dateNaissance + " mais obtenue : " + personne.getDateNaissance());
        } else {
            System.out.println("Date de naissance : OK");
        }

        if (dateDeces == null) {
            if (personne.getDateDeces() != null) {
                System.out.println("Échec : date de décès attendue null mais obtenue : " + personne.getDateDeces());
            } else {
                System.out.println("Date de décès : OK (null)");
            }
        } else {
            if (!dateDeces.equals(personne.getDateDeces())) {
                System.out.println("Échec : la date de décès attendue est " + dateDeces + " mais obtenue : " + personne.getDateDeces());
            } else {
                System.out.println("Date de décès : OK");
            }
        }
    }

    /**
     * Tests the date validation functionality.
     * Verifies that the system correctly validates birth and death dates,
     * ensuring that birth date is before death date.
     * Tests both valid and invalid date combinations.
     */
    public static void testVerifierCoherenceDates() {
        String nom = "nomPersonne";
        String prenom = "prenomPersonne";
        String nationalite = "France";

        Calendar calendrier = Calendar.getInstance();
        calendrier.set(2000, Calendar.JANUARY, 1);  // 1er janvier 2000
        Date dateNaissance = calendrier.getTime();

        Calendar calendrier2 = Calendar.getInstance();
        calendrier2.set(1990, Calendar.MAY, 15);  // 15 Mai 1990
        Date dateDeces = calendrier2.getTime();

        Personne personne = new Personne(nom, prenom, nationalite, dateNaissance, dateDeces);

        // Vérifie que la date de naissance est bien avant la date de décès
        // Ici une erreur est attendue car dateNaissance > dateDeces
        System.out.print("L'erreur suivante est attendue : ");
        try {
            Personne.verifierCoherenceDates(personne.getDateNaissance(), personne.getDateDeces());
            System.out.println("Échec : exception attendue mais non levée");
        } catch (Exception e) {
            System.out.println("OK - Exception levée : " + e.getMessage());
        }

        // Inverse les dates (maintenant dateNaissance < dateDeces)
        personne.setDateNaissance(dateDeces);
        personne.setDateDeces(dateNaissance);

        try {
            boolean resultat = Personne.verifierCoherenceDates(personne.getDateNaissance(), personne.getDateDeces());
            if (resultat) {
                System.out.println("Vérification des dates : OK");
            } else {
                System.out.println("Vérification des dates : Échec - résultat false inattendu");
            }
        } catch (Exception e) {
            System.out.println("Échec - exception inattendue : " + e.getMessage());
        }
    }
}
