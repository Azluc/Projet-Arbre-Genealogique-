package com.cytech.gestionBDD;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	 public static void AjouterUtilisateur(String nom, String prenom, String dateNaissance,
		        String nationalite, String numeroSecuriteSociale, String email,
		        String motDePasse, int codePublic, int codePrive,
		        String adresse, String telephone, byte[] photo, byte[] carte_identite) {

		    try {
		    	/*
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String dateStr = sdf.format(dateNaissance);*/

		        String valPhoto = (photo == null) ? "NULL" : "'" + photo + "'";
		        String valCarteId = (carte_identite == null) ? "NULL" : "'" + carte_identite + "'";

		        String query = "INSERT INTO utilisateur (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, mot_de_passe, code_public, code_prive, adresse, telephone, photo, carte_identite) VALUES ('" +
		        	    nom + "', '" +
		        	    prenom + "', '" +
		        	    dateNaissance + "', '" +
		        	    nationalite + "', '" +  
		        	    numeroSecuriteSociale + "', '" +   
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
	 
	 
	 public static List<Object> getPrenomEtMotDePasseParCodePrive(int codePrive) {
		    List<Object> infos = new ArrayList<>();
		    Connection connexion = null;
		    PreparedStatement statement = null;
		    ResultSet resultat = null;

		    try {
		        connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/arbre_genealogique", "user", "Password123!");
		        String sql = "SELECT prenom, mot_de_passe, code_prive FROM utilisateur WHERE code_prive = ?";
		        statement = connexion.prepareStatement(sql);
		        statement.setInt(1, codePrive); // ici c’est bien setString

		        resultat = statement.executeQuery();

		        if (resultat.next()) {
		            infos.add(resultat.getString("prenom"));
		            infos.add(resultat.getString("mot_de_passe"));
		            infos.add(resultat.getString("code_prive"));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (resultat != null) resultat.close();
		            if (statement != null) statement.close();
		            if (connexion != null) connexion.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }

		    return infos;
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
	


