package com.cytech.classeTestsUnitaires;

/**
 * Main test runner class for the genealogical tree application.
 * This class orchestrates the execution of all test classes in the application,
 * running them in sequence and providing clear separation between different test suites.
 * It includes tests for:
 * - Administrator functionality
 * - Family side (Cote) functionality
 * - Gender (Genre) functionality
 * - Relationship (Lien) functionality
 * - Person (Personne) functionality
 * - User (Utilisateur) functionality
 * - Database management for membership requests
 * - Database management for family relationships
 * - Database management for persons
 * - Database management for users
 */
public class Main {

	/**
	 * Main method to run all test suites.
	 * Executes each test class in sequence and provides clear output
	 * indicating the start and completion of each test suite.
	 * 
	 * @param args command line arguments (not used)
	 * @throws Exception if any test throws an exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("---- Test Administrateur----");
		TestAdministrateur.main(null);
		System.out.println("---- Test Administrateur validé !----");
		
		
		System.out.println("---- Test Coté ----");
		TestCote.main(null);
		System.out.println("---- Test Coté validé !----");
		
		System.out.println("---- Test Genre----");
		TestGenre.main(null);
		System.out.println("---- Test Genrevalidé !----");
		
		System.out.println("---- Test Lien ----");
		TestLien.main(null);
		System.out.println("---- Test Lien validé !----");

		System.out.println("---- Test Personne ----");
		TestPersonne.main(null);
		System.out.println("---- Test Personne validé !----");
		
		System.out.println("---- Test Utilisateur ----");
		TestUtilisateur.main(null);
		System.out.println("---- Test Utilisateur validé !----");
		
		//Test demande adhesion base de données
		TestGestionDemandeAdhesionBdd.main(null);
		
		//Test gestion lien parente base de données
		TestGestionLienParenteBDD.main(null);
		
		//Test gestion personne base dedonnées
		TestGestionPersonneBDD.main(null);
		
		//Test gestion utilistareur base de données
		TestGestionUtilisateurBDD.main(null);
		
 
		
	}

}
