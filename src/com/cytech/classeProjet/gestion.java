package com.cytech.classeProjet;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class gestion {
	static Connection cnx;
	static Statement st;
	static ResultSet rst;
	
	public static void main(String[] args) {
		
		rechercheparNP("Dupont","Jean");
		try {
			cnx=connecterDB();
			st=cnx.createStatement();
			rst=st.executeQuery("Select * From utilisateur");
			/**
			 * test affichage de la bdd utilisateur
			 */
			while (rst.next()) {
				System.out.println("ID: " + rst.getInt("id") + "\t");
				System.out.println("Nom: " + rst.getString("nom") + "\t");
				System.out.println("Prénom: " + rst.getString("prenom") + "\t");
				System.out.println("Date de naissance: " + rst.getDate("date_naissance") + "\t");
				System.out.println("Nationalité: " + rst.getString("nationalite") + "\t");
				System.out.println("N° Sécu: " + rst.getDouble("numero_securite_sociale") + "\t");
				System.out.println("Email: " + rst.getString("email") + "\t");
				System.out.println("Mot de passe: " + rst.getString("mot_de_passe") + "\t");
				System.out.println("Code public: " + rst.getDouble("code_public") + "\t");
				System.out.println("Code privé: " + rst.getDouble("code_prive") + "\t");
				System.out.println("Adresse: " + rst.getString("adresse") + "\t");
				System.out.println("Téléphone: " + rst.getString("telephone") + "\t");
				System.out.println("Photo: " + rst.getString("photo") + "\t");
				System.out.println("Carte ID: " + rst.getString("carte_identite") + "\t");
				System.out.println("--------------------------------------------------");


			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	 public static Connection connecterDB() {
	    	try {
	    		Class.forName("com.mysql.cj.jdbc.Driver");
	    		System.out.println("ok");
	    		String url="jdbc:mysql://localhost:3306/arbre_genealogique";
	    		String user="root";
	    		String password="";
	    		Connection cnx=DriverManager.getConnection(url, user, password);
	    		System.out.println("Connexion etablie");
	    		return cnx;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	 }
	 	public static void AjouterU(int id,String nom,String prenom,Date dateNaissance,
	 			String nationalite,Double numeroSecuriteSociale,String email,
	 			String motDePasse,Double codePublic,Double codePrive,
	 			String adresse,String telephone,String photo,String carte_identite){
	 		
	 		try {
	 			String query = "INSERT INTO utilisateur VALUES(" +
	 				    id + ", '" +
	 				    nom + "', '" +
	 				    prenom + "', '" +
	 				    dateNaissance + "', '" +
	 				    nationalite + "', " +
	 				    numeroSecuriteSociale + ", '" +
	 				    email + "', '" +
	 				    motDePasse + "', " +
	 				    codePublic + ", " +
	 				    codePrive + ", '" +
	 				    adresse + "', '" +
	 				    telephone + "', '" +
	 				    photo + "', '" +
	 				    carte_identite + "')";

	 			cnx=connecterDB();
	 			st=cnx.createStatement();
	 			st.executeUpdate(query);
	 			System.out.println("Utilisateur ajouté");
	 			
	 		}catch(Exception e){
	 			System.out.println(e.getMessage());
	 		}
	 	}
	 	public static void rechercheparNP(String nom,String prenom) {
	 		try{
	 		
	 			String query = "SELECT * FROM UTILISATEUR WHERE nom='" + nom + "' AND prenom='" + prenom + "'";

	 		
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
	


