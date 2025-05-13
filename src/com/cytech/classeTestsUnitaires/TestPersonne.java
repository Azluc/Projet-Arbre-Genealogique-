package com.cytech.classeTestsUnitaires;
 
import java.util.Calendar;
import java.util.Date;

 
import com.cytech.classeProjet.Personne;

import junit.framework.*;

public class TestPersonne extends TestCase{

	public static void main(String[] args) {
		testConstructeurPersonne();
		testVerifierCoherenceDates();
	}

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
		assertEquals(prenom,personne.getPrenom());
		assertEquals(nom,personne.getNom());
		assertEquals(nationalite,personne.getNationalite());
		assertEquals(dateNaissance,personne.getDateNaissance());
		assertEquals(dateDeces,personne.getDateDeces());	 
	}
	
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
		// Si ce n'est pas le cas, on lève une exception
		System.out.print("L'erreur suivante est attendue : ");
		try {
			// Dans ce cas, une erreur est attendue car la date de naissance est avant la date de naissance
			Personne.verifierCoherenceDates(personne.getDateNaissance(),personne.getDateDeces());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		personne.setDateNaissance(dateDeces);
		personne.setDateDeces(dateNaissance);
		try {
			assertTrue(Personne.verifierCoherenceDates(personne.getDateNaissance(),personne.getDateDeces()));
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	 
	 
	
	
	
	
	
	
}
