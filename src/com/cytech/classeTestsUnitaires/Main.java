package com.cytech.classeTestsUnitaires;

public class Main {

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
