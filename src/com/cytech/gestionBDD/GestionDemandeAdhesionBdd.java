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

/**
 * Class managing database operations for membership requests.
 */
public class GestionDemandeAdhesionBdd {
	public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
    public static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    
    
    	    private static Connection connexion;

    	    /**
    	     * Constructor: opens the database connection.
    	     * 
    	     * @throws SQLException If a database error occurs
    	     */
    	    public GestionDemandeAdhesionBdd() throws SQLException {
    	        GestionDemandeAdhesionBdd.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
    	    }

    	    /**
    	     * Closes the database connection properly.
    	     */
    	    public void fermerConnexion() {
    	        try {
    	            if (connexion != null && !connexion.isClosed()) {
    	                connexion.close();
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    /**
    	     * Retrieves the photo and ID card links for a given email.
    	     * 
    	     * @param email The email address to search for
    	     * @return A list containing the photo and ID card bytes
    	     * @throws SQLException If a database error occurs
    	     */
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

    	        return images; // empty if nothing found
    	    }
    
    /**
     * Inserts a membership request into the database.
     * 
     * @param nom Last name
     * @param prenom First name
     * @param email Email address
     * @param adresse Address
     * @param nationalite Nationality
     * @param dateNaissance Birth date
     * @param telephone Phone number
     * @param numeroSS Social security number
     * @param pieceIdentite ID card file path
     * @param photoNumerique Digital photo file path
     */
	public static void insertionDemandeBaseDonnes(String nom, String prenom, String email, String adresse, String nationalite, Date dateNaissance, String telephone, String numeroSS, String pieceIdentite, String photoNumerique) {
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
         
        String sql = "INSERT INTO demande_adhesion (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, photoLien,carte_identite,carte_identiteLien) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement cursor = conn.prepareStatement(sql)) {
            
            cursor.setString(1, nom);
        	cursor.setString(2, prenom);
        	cursor.setDate(3, new java.sql.Date(dateNaissance.getTime()));
            cursor.setString(4, nationalite);
            cursor.setString(5, numeroSS);
            cursor.setString(6, email);
            cursor.setString(7, adresse);
            cursor.setString(8, telephone);

            // Read image files and convert to byte arrays
            File fichierPhoto = new File(photoNumerique);
            byte[] photoBytes = Files.readAllBytes(fichierPhoto.toPath());
            
            File fichierCarteIdentite = new File(pieceIdentite);
            byte[] carteIdentiteBytes = Files.readAllBytes(fichierCarteIdentite.toPath());

            // Insert BLOBs (photo and ID card)
            cursor.setBytes(9, photoBytes);
            cursor.setString(10, photoNumerique);
            cursor.setBytes(11, carteIdentiteBytes);
            cursor.setString(12, pieceIdentite);
            cursor.executeUpdate();
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Error during database insertion.");
        }
    }
	
	/**
	 * Verifies if an email address is unique in the membership requests.
	 * 
	 * @param mail The email address to check
	 * @return true if the email is unique, false otherwise
	 */
	public static boolean verifierMailUnique(String mail) {
	    String url = "jdbc:mysql://localhost:3306/arbre_genealogique";  
	    String sql = "SELECT * FROM demande_adhesion WHERE email = ?";  
	    
	    try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
	         PreparedStatement requete = connexion.prepareStatement(sql)) {

	        requete.setString(1, mail);

	        var resultat = requete.executeQuery();

	        // If result exists, email is already in database
	        if (resultat.next()) {
	            return false; // Email already exists
	        } else {
	            return true; // Email is unique
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}
}
