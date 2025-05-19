package com.cytech.classeTestsUnitaires;

 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cytech.gestionBDD.GestionUtilisateurBDD;

public class TestGestionUtilisateurBDD {

	public static void main(String[] args) {
	    testInsertion();
	    testRechercheUtilisateur();
	    testModifierUtilisateur();
	}
	public static void testInsertion() {
	    try {
	    	//Erreur a corriger pour la gestion Utilisateur !!!!!!!!!!!!!!!!!
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date dateNaissance = sdf.parse("1990-01-01");

	        GestionUtilisateurBDD.AjouterUtilisateur("TestNom", "TestPrenom", dateNaissance,
	                "Française", 1234567890123.0, "test.email@example.com",
	                "mdp123", 111.0, 222.0, "1 rue Test", "0123456789", null, null);

	        System.out.println("testInsertion : Utilisateur ajouté avec succès.");

	    } catch (Exception e) {
	        System.out.println("Erreur dans testInsertion : " + e.getMessage());
	    }
	}

	public static void testRechercheUtilisateur() {
	    String nom = "TestNom";
	    String prenom = "TestPrenom";

	    System.out.println("Début de la recherche de l'utilisateur ajouté...");
	    GestionUtilisateurBDD.rechercheparNP(nom, prenom);
	    System.out.println("testRechercheUtilisateur : Recherche terminée.");
	}

	public static void testModifierUtilisateur() {
	    String nom = "TestNom";
	    String prenom = "TestPrenom";

	    try {
	        Connection cnx = GestionUtilisateurBDD.connecterDB();
	        Statement st = cnx.createStatement();

	        // Requête pour modifier directement en utilisant nom et prenom
	        String nouveauMdp = "nouveauMdp123";
	        String nouvelEmail = "nouvel.email@example.com";
	        String nouveauTel = "0605040302";
	        String nouvelleAdresse = "99 avenue Modif";

	        String queryUpdate = "UPDATE utilisateur SET " +
	                "mot_de_passe = '" + nouveauMdp + "', " +
	                "email = '" + nouvelEmail + "', " +
	                "telephone = '" + nouveauTel + "', " +
	                "adresse = '" + nouvelleAdresse + "' " +
	                "WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'";

	        int nbLignesModifiees = st.executeUpdate(queryUpdate);

	        if (nbLignesModifiees > 0) {
	            System.out.println("testModifierUtilisateur : " + nbLignesModifiees + " utilisateur(s) modifié(s) avec succès.");
	        } else {
	            System.out.println("testModifierUtilisateur : Aucun utilisateur trouvé avec nom='" + nom + "' et prenom='" + prenom + "'.");
	        }

	        // Vérification des modifications : on relit les infos modifiées
	        String queryVerif = "SELECT mot_de_passe, email, telephone, adresse FROM utilisateur WHERE nom='" + nom + "' AND prenom='" + prenom + "'";
	        ResultSet rsVerif = st.executeQuery(queryVerif);

	        while (rsVerif.next()) {
	            System.out.println("Mot de passe modifié : " + rsVerif.getString("mot_de_passe"));
	            System.out.println("Email modifié : " + rsVerif.getString("email"));
	            System.out.println("Téléphone modifié : " + rsVerif.getString("telephone"));
	            System.out.println("Adresse modifiée : " + rsVerif.getString("adresse"));
	        }

	        rsVerif.close();
	        st.close();
	        cnx.close();

	    } catch (Exception e) {
	        System.out.println("Erreur dans testModifierUtilisateur : " + e.getMessage());
	    }
	}
    
}
     
 
 
 