package com.cytech.gestionBDD;

import java.sql.*;

import com.cytech.classeProjet.Personne;

public class GestionPersonneBDD {
	private final static String utilisateur = "user";
	private final static String motDePasse = "Password123!";
	public static void ajouterPersonneRacine(Personne p, int idArbre) {
		
		
	    try {
	        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
 

	        Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);

	        String sql = "INSERT INTO Personne (id_arbre, nom, prenom, profondeur, dateNaissance, dateDeces, nomRelation, coteLien) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, idArbre);
	        stmt.setString(2, p.getNom());
	        stmt.setString(3, p.getPrenom());
	        stmt.setInt(4, p.getProfondeur());
	        stmt.setDate(5, new java.sql.Date(p.getDateNaissance().getTime()));

	        // Forcer à NULL
	        stmt.setNull(6, java.sql.Types.DATE);      // dateDeces
	        stmt.setNull(7, java.sql.Types.VARCHAR);   // nomRelation
	        stmt.setNull(8, java.sql.Types.VARCHAR);   // coteLien

	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
