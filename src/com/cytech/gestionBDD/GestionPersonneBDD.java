package com.cytech.gestionBDD;

import java.sql.*;

import com.cytech.classeProjet.Personne;

/**
 * Class managing database operations for Person objects.
 */
public class GestionPersonneBDD {
	private final static String utilisateur = "user";
	private final static String motDePasse = "Password123!";

	/**
	 * Adds a root person to the database.
	 * 
	 * @param p The person to add
	 * @param idArbre The ID of the tree this person belongs to
	 */
	public static void ajouterPersonneRacine(Personne p, int idArbre) {
	    try {
	        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
	        Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);

	        String sql = "INSERT INTO Personne (id_arbre, nom, prenom, dateNaissance, dateDeces, profondeur, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, idArbre);
	        stmt.setString(2, p.getNom());
	        stmt.setString(3, p.getPrenom());
	        stmt.setDate(4, new java.sql.Date(p.getDateNaissance().getTime()));

	        // Handle death date: null or actual date
	        if (p.getDateDeces() != null) {
	            stmt.setDate(5, new java.sql.Date(p.getDateDeces().getTime()));
	        } else {
	            stmt.setNull(5, java.sql.Types.DATE);
	        }

	        stmt.setInt(6, p.getProfondeur());

	        // Handle gender: null or actual value
	        if (p.getGenre() != null) {
	            stmt.setString(7, p.getGenre().toString());
	        } else {
	            stmt.setNull(7, java.sql.Types.VARCHAR);
	        }

	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Adds a person to the database.
	 * 
	 * @param p The person to add
	 * @param idArbre The ID of the tree this person belongs to
	 */
	public static void ajouterPersonne(Personne p, int idArbre) {
	    try {
	        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
	        Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);

	        String sql = "INSERT INTO Personne (id_arbre, nom, prenom, dateNaissance, dateDeces, profondeur, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, idArbre);
	        stmt.setString(2, p.getNom());
	        stmt.setString(3, p.getPrenom());
	        stmt.setDate(4, new java.sql.Date(p.getDateNaissance().getTime()));

	        // Handle death date: null or actual date
	        if (p.getDateDeces() != null) {
	            stmt.setDate(5, new java.sql.Date(p.getDateDeces().getTime()));
	        } else {
	            stmt.setNull(5, java.sql.Types.DATE);
	        }

	        stmt.setInt(6, p.getProfondeur());

	        // Handle gender: null or actual value
	        if (p.getGenre() != null) {
	            stmt.setString(7, p.getGenre().toString());
	        } else {
	            stmt.setNull(7, java.sql.Types.VARCHAR);
	        }

	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
