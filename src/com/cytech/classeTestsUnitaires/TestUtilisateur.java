package com.cytech.classeTestsUnitaires;

import java.util.*;
import com.cytech.classeProjet.Utilisateur;
import junit.framework.TestCase;
public class TestUtilisateur extends TestCase {

    public static void main(String[] args) {
        testConstructeurUtilisateur();
        testVerifierCoherenceDatesUtilisateur();
    }

    public static void testConstructeurUtilisateur() {
        // Données de test
        String nom = "Dupont";
        String prenom = "Jean";
        String nationalite = "Française";

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date dateNaissance = cal.getTime();

        cal.set(0, 0, 0); // Date de décès nulle pour utilisateur vivant
        Date dateDeces = null;

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

        // Vérification des attributs hérités
        assertEquals(nom, utilisateur.getNom());
        assertEquals(prenom, utilisateur.getPrenom());
        assertEquals(nationalite, utilisateur.getNationalite());
        assertEquals(dateNaissance, utilisateur.getDateNaissance());
        assertNull(utilisateur.getDateDeces());

        // Vérification des attributs spécifiques
        assertEquals(codePublic, utilisateur.getCodePublic());
        assertEquals(codePrive, utilisateur.getCodePrive());
        assertEquals(email, utilisateur.getEmail());
        assertEquals(motDePasse, utilisateur.getMotDePasse());
        assertEquals(numeroSecuriteSociale, utilisateur.getNumeroSecuriteSociale());
        assertEquals(telephone, utilisateur.getTelephone());
        assertEquals(adresse, utilisateur.getAdresse());
    }
  
    	public static void testVerifierCoherenceDatesUtilisateur() {
    	    // Dates cohérentes : naissance avant décès
    	    Calendar cal = Calendar.getInstance();
    	    cal.set(1980, Calendar.JANUARY, 1);
    	    Date dateNaissance = cal.getTime();

    	    cal.set(2020, Calendar.JANUARY, 1);
    	    Date dateDeces = cal.getTime();

    	    // Attributs spécifiques à Utilisateur
    	    String nom = "Durand";
    	    String prenom = "Claire";
    	    String nationalite = "Française";
    	    Double codePublic = 11111.0;
    	    Double codePrive = 22222.0;
    	    String email = "claire.durand@example.com";
    	    String motDePasse = "mdpTest";
    	    Double numeroSecuriteSociale = 2987654321123.0;
    	    String telephone = "0708091011";
    	    String adresse = "456 avenue République, 75011 Paris";

    	    // Création d’un Utilisateur avec dates valides
    	    Utilisateur utilisateurV = new Utilisateur(
    	        nom, prenom, nationalite, dateNaissance, dateDeces,
    	        codePublic, codePrive, email, motDePasse,
    	        numeroSecuriteSociale, telephone, adresse
    	    );

    	    try {
    	        assertTrue(Utilisateur.verifierCoherenceDates(utilisateurV.getDateNaissance(), utilisateurV.getDateDeces()));
    	        System.out.println(" Test passé : dates valides.");
    	    } catch (Exception e) {
    	        fail(" Erreur inattendue : " + e.getMessage());
    	    }

    	    // Dates incohérentes : décès avant naissance
    	    cal.set(1970, Calendar.JANUARY, 1);
    	    Date dateDecesInvalide = cal.getTime();

    	    utilisateurV.setDateDeces(dateDecesInvalide);

    	    try {
    	        Utilisateur.verifierCoherenceDates(utilisateurV.getDateNaissance(), utilisateurV.getDateDeces());
    	        fail("Une exception aurait dû être levée pour des dates incohérentes !");
    	    } catch (Exception e) {
    	        System.out.println("Exception attendue: " + e.getMessage());
    	    }
    	}

}

