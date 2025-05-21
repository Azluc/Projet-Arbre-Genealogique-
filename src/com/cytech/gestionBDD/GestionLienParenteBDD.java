package com.cytech.gestionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;

public class GestionLienParenteBDD {
	
	public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
    public static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
	
	public static void enregistrerLienDansBDD(Personne p1, Personne p2, TypeRelation typeRelation, String nomRelation) {
		System.out.println("dans la bdd, la relation vaut"+nomRelation);
	    String sql = "";
	    try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse)) {
	        switch (typeRelation) {
	            case PARENT_ENFANT:
	                sql = "INSERT IGNORE INTO Parent_Enfant (id_arbre, nom_parent, prenom_parent, nom_enfant, prenom_enfant, nomRelation) VALUES (?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                    stmt.setInt(1, p1.getId_arbre());
	                    stmt.setString(2, p1.getNom());
	                    stmt.setString(3, p1.getPrenom());
	                    stmt.setString(4, p2.getNom());
	                    stmt.setString(5, p2.getPrenom());
	                    stmt.setString(6, nomRelation);
	                    stmt.executeUpdate();
	                }
	                break;

	            case FRERE_SOEUR:
	                sql = "INSERT IGNORE INTO Frere_Soeur (id_arbre, nom1, prenom1, nom2, prenom2, nomRelation) VALUES (?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                    stmt.setInt(1, p1.getId_arbre());
	                    stmt.setString(2, p1.getNom());
	                    stmt.setString(3, p1.getPrenom());
	                    stmt.setString(4, p2.getNom());
	                    stmt.setString(5, p2.getPrenom());
	                    stmt.setString(6, nomRelation);
	                    stmt.executeUpdate();
	                }
	                break;

	            default:
	                // On ignore les autres types comme UNION
	                break;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
