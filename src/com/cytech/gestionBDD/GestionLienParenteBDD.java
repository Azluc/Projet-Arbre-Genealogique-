package com.cytech.gestionBDD;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;

/**
 * Class managing database operations for family relationships.
 */
public class GestionLienParenteBDD {
	
	public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
    public static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
	
    /**
     * Retrieves the set of deceased people in a given tree.
     * 
     * @param idArbre The ID of the tree to search in
     * @return A set of first names of deceased people
     */
    public static Set<String> recupererPersonnesDecedees(int idArbre) {
        Set<String> decedes = new HashSet<>();

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            String requete = "SELECT prenom FROM Personne WHERE id_arbre = ? AND dateDeces IS NOT NULL";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idArbre);

            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                String prenom = resultat.getString("prenom");
                decedes.add(prenom);
            }

            resultat.close();
            statement.close();
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decedes;
    }

    /**
     * Retrieves the set of living people in a given tree.
     * 
     * @param idArbre The ID of the tree to search in
     * @return A set of first names of living people
     */
    public static Set<String> recupererPersonnesVivantes(int idArbre) {
        Set<String> vivants = new HashSet<>();

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            String requete = "SELECT prenom FROM Personne WHERE id_arbre = ? AND dateDeces IS NULL";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idArbre);

            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                String prenom = resultat.getString("prenom");
                vivants.add(prenom);
            }

            resultat.close();
            statement.close();
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vivants;
    }

    /**
     * Retrieves the set of women in a given tree.
     * 
     * @param idArbre The ID of the tree to search in
     * @return A set of first names of women
     */
    public static Set<String> recupererFemmes(int idArbre) {
        Set<String> femmes = new HashSet<>();

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            String requete = "SELECT prenom FROM Personne WHERE id_arbre = ? AND genre = 'FEMME'";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idArbre);

            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                String prenom = resultat.getString("prenom");
                femmes.add(prenom);
            }

            resultat.close();
            statement.close();
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return femmes;
    }

    /**
     * Retrieves the set of men in a given tree.
     * 
     * @param idArbre The ID of the tree to search in
     * @return A set of first names of men
     */
    public static Set<String> recupererHommes(int idArbre) {
        Set<String> hommes = new HashSet<>();

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            String requete = "SELECT prenom FROM Personne WHERE id_arbre = ? AND genre = 'HOMME'";
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setInt(1, idArbre);

            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                String prenom = resultat.getString("prenom");
                hommes.add(prenom);
            }

            resultat.close();
            statement.close();
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hommes;
    }
    
    /**
     * Saves a family relationship between two people in the database.
     * 
     * @param p1 The first person
     * @param p2 The second person
     * @param typeRelation The type of relationship (parent-child or sibling)
     * @param nomRelation The specific name of the relationship
     */
	public static void enregistrerLienDansBDD(Personne p1, Personne p2, TypeRelation typeRelation, String nomRelation) {
		 
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
	                // Ignore other types like UNION
	                break;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
