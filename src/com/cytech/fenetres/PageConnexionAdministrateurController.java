package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cytech.Main;

import javafx.event.ActionEvent;

public class PageConnexionAdministrateurController {
    public static  String utilisateurBDD = "user";
    public static String motDePasseBDD ="Password123!";
    
   
	private Main main;
	@FXML
	private TextField codePrive;
	@FXML
	private TextField motDePasseAdministrateur;
	@FXML
	private Button boutonRetour;
	
	
    // Fonction pour vérifier le mot de passe de l'administrateur
    public boolean verifierMotDePasseAdmin(String identifiant, String motDePasse) {
        // Variables de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique"; // URL de la base de données
         

        // Requête SQL pour vérifier l'identifiant et le mot de passe
        String sql = "SELECT mot_de_passe FROM administrateur WHERE identifiant = ?";

        // Connexion à la base de données
        try (Connection conn = DriverManager.getConnection(url, utilisateurBDD, motDePasseBDD);
             PreparedStatement cursor = conn.prepareStatement(sql)) {

            
        	cursor.setString(1, identifiant);

        	
            // Exécuter la requête et récupérer les résultats
            try (ResultSet rs = cursor.executeQuery()) {
                if (rs.next()) {
                     
                    String motDePasseStocke = rs.getString("mot_de_passe");

                    // Comparer le mot de passe saisi avec celui stocké
                    if (motDePasseStocke.equals(motDePasse)) {
                    	System.out.println(motDePasse);
                    	System.out.println(motDePasseStocke);
                    	// Cas le mot de passe est valide
                    	return true; 
                    } else {
                    	// cas ou le mot de passe n'est pas valide
                        return false; 
                    }
                } else {
                	// Cas ou l'identifiant n'a pas ete trouvé dans la bdd
                    return false; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Erreur lors de la connexion ou de l'exécution de la requête
        }
    }
	
	// Event Listener on Button.onAction
	@FXML
	public void ConnexionBoutonAdministrateur(ActionEvent event) {
		String identifiant = codePrive.getText();
		String motDePasse = motDePasseAdministrateur.getText();
	
		if (verifierMotDePasseAdmin(identifiant, motDePasse)) {
			main.afficherPageAdministrateur();
		}
		else{
			Alert alerte = new Alert(AlertType.ERROR);
			alerte.initOwner(main.getPrimaryStage());
			alerte.setTitle("Connexion impossible");
			alerte.setHeaderText("La connexion n'a pu aboutir");
			alerte.setContentText("Les identifiants saisis sont incorrect !");
			alerte.showAndWait();
		}
		
	}
	// Event Listener on Button[#boutonRetour].onAction
	@FXML
	public void retourBoutonPageConnexionUtilisateur(ActionEvent event) {
	    if (main != null) {
	        main.afficherAccueil(); // Utilise ta méthode centralisée
	    } else {
	        System.err.println("ERREUR : main vaut null !");
	    }
	}
	public void setMain(Main main) {
	    this.main = main;
	}
}
