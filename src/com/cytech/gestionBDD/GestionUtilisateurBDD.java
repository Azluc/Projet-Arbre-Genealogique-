package com.cytech.gestionBDD;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class GestionUtilisateurBDD {
	static Connection cnx;
	static Statement st;
	static ResultSet rst;
	
	 
	 public static Connection connecterDB() {
	    	try {
	    		Class.forName("com.mysql.cj.jdbc.Driver");
	    		System.out.println("ok");
	    		String url="jdbc:mysql://localhost:3306/arbre_genealogique";
	    		String user="user";
	    		String password="Password123!";
	    		Connection cnx=DriverManager.getConnection(url, user, password);
	    		System.out.println("Connexion etablie");
	    		return cnx;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	 }
	 public static void AjouterUtilisateur(String nom, String prenom, Date dateNaissance,
		        String nationalite, Double numeroSecuriteSociale, String email,
		        String motDePasse, Double codePublic, Double codePrive,
		        String adresse, String telephone, String photo, String carte_identite) {

		    try {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String dateStr = sdf.format(dateNaissance);

		        String valPhoto = (photo == null) ? "NULL" : "'" + photo + "'";
		        String valCarteId = (carte_identite == null) ? "NULL" : "'" + carte_identite + "'";

		        String query = "INSERT INTO utilisateur (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, mot_de_passe, code_public, code_prive, adresse, telephone, photo, carte_identite) VALUES ('" +
		                nom + "', '" +
		                prenom + "', '" +
		                dateStr + "', '" +
		                nationalite + "', " +
		                numeroSecuriteSociale + ", '" +
		                email + "', '" +
		                motDePasse + "', " +
		                codePublic + ", " +
		                codePrive + ", '" +
		                adresse + "', '" +
		                telephone + "', " +
		                valPhoto + ", " +
		                valCarteId + ")";

		        cnx = connecterDB();
		        st = cnx.createStatement();
		        st.executeUpdate(query);
		        System.out.println("Utilisateur ajouté");

		    } catch (Exception e) {
		        System.out.println("Erreur lors de l'insertion : " + e.getMessage());
		    }
		}
	 	public static void rechercheparNP(String nom,String prenom) {
	 		try{
	 		
	 			String query = "SELECT * FROM utilisateur WHERE nom='" + nom + "' AND prenom='" + prenom + "'";

	 		
	 		cnx=connecterDB();
 			st=cnx.createStatement();
 			rst=st.executeQuery(query);	
 			if (rst.next()) {
 		        System.out.println("Il existe un utilisateur portant le même nom et prénom");
 		    } else {
 		        System.out.println("Utilisateur non existant");
 			}
 			
	 		}catch(Exception e)	{
	 			System.out.println(e.getMessage());
	 		}
	 	}
	 	
	 	public static void MofifierU(int id, String n_mdp, String n_email,String n_telephone,String n_adresse) {
	 		try {
	 			String query = "UPDATE utilisateur SET mot_de_passe='" + n_mdp
	 	             + "', email=" + n_email
	 	             + ", telephone=" + n_telephone
	 	             + ", adresse='" + n_adresse
	 	             + " WHERE id=" + id + ";";
	 			cnx=connecterDB();
	 			st=cnx.createStatement();
	 			st.executeUpdate(query);
	 			System.out.println("Utilisateur modifié");

	 			
	 			
	 		}catch(Exception e) {
	 			System.out.println(e.getMessage());
	 		}
	 	}

	 
}
	


