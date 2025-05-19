package com.cytech.gestionBDD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 
public class GestionDemandeAdhesionBdd {
	public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
    public static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    
    
    	    private static Connection connexion;

    	    // Constructeur : ouvre la connexion
    	    public GestionDemandeAdhesionBdd() throws SQLException {
    	        GestionDemandeAdhesionBdd.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
    	    }

    	    // Fermer la connexion proprement
    	    public void fermerConnexion() {
    	        try {
    	            if (connexion != null && !connexion.isClosed()) {
    	                connexion.close();
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    // Récupère les liens photoLien et carte_identiteLien pour un email donné
    	    public static List<byte[]> getImagesParEmail(String email) throws SQLException {
    	        List<byte[]> images = new ArrayList<>();
 

    	        try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
    	             PreparedStatement stmt = connexion.prepareStatement(
    	                 "SELECT photo, carte_identite FROM demande_adhesion WHERE email = ?")) {
    	            
    	            stmt.setString(1, email);
    	            try (ResultSet rs = stmt.executeQuery()) {
    	                if (rs.next()) {
    	                    byte[] photoBytes = rs.getBytes("photo");
    	                    byte[] carteBytes = rs.getBytes("carte_identite");

    	                    images.add(photoBytes);
    	                    images.add(carteBytes);
    	                }
    	            }
    	        }

    	        return images; // vide si rien trouvé
    	    }
    
	public static void insertionDemandeBaseDonnes(String nom, String prenom, String email, String adresse, String nationalite, Date dateNaissance, String telephone, String numeroSS, String pieceIdentite, String photoNumerique) {
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique"; // Remplacez par l'URL de votre base de données
         

        // Requête SQL pour insérer les données
        String sql = "INSERT INTO demande_adhesion (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, photoLien,carte_identite,carte_identiteLien) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement cursor = conn.prepareStatement(sql)) {
            
            // Définir les paramètres de la requête
        	cursor.setString(1, nom);
        	cursor.setString(2, prenom);
        	cursor.setDate(3, new java.sql.Date(dateNaissance.getTime()));
            cursor.setString(4, nationalite);
            cursor.setString(5, numeroSS);
            cursor.setString(6, email);
            cursor.setString(7, adresse);
            cursor.setString(8, telephone);

            // Lire le fichier image et le convertir en tableau de bytes
            File fichierPhoto = new File(photoNumerique);
            byte[] photoBytes = Files.readAllBytes(fichierPhoto.toPath());
            
            File fichierCarteIdentite = new File(pieceIdentite);
            byte[] carteIdentiteBytes = Files.readAllBytes(fichierCarteIdentite.toPath());

            // Insérer les BLOBs (photo et carte d'identité)
            cursor.setBytes(9, photoBytes);
            cursor.setString(10, photoNumerique);
            cursor.setBytes(11, carteIdentiteBytes);
            cursor.setString(12, pieceIdentite);
            // Exécution de la requête
            cursor.executeUpdate();
            
             
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion dans la base de données.");
        }
    }
	
	public static boolean verifierMailUnique(String mail) {
	    String url = "jdbc:mysql://localhost:3306/arbre_genealogique"; // URL de la BDD
	    String sql = "SELECT * FROM demande_adhesion WHERE email = ?"; // Requête pour vérifier le mail
	    
	    try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
	         PreparedStatement requete = connexion.prepareStatement(sql)) {

	        requete.setString(1, mail); // Remplace ? par le mail

	        // Exécuter la requête et vérifier si une ligne est retournée
	        var resultat = requete.executeQuery();

	        // Si le résultat existe, cela veut dire que le mail est déjà dans la base
	        if (resultat.next()) {
	            return false; // Le mail existe déjà
	        } else {
	            return true; // Le mail est unique
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // En cas d'erreur, retourner false (on ne sait pas si le mail est unique)
	    }
	}
	
}
