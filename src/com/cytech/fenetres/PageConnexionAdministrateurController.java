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

/**
 * Controller for the administrator login page.
 * Handles administrator authentication and navigation to the administrator interface.
 */
public class PageConnexionAdministrateurController {
    /** Database username */
    public static String utilisateurBDD = "user";
    
    /** Database password */
    public static String motDePasseBDD = "Password123!";
    
    /** Reference to the main application */
    private Main main;
    
    /** Text field for entering administrator ID */
    @FXML
    private TextField codePrive;
    
    /** Text field for entering administrator password */
    @FXML
    private TextField motDePasseAdministrateur;
    
    /** Button to return to previous page */
    @FXML
    private Button boutonRetour;
    
    /**
     * Verifies the administrator's password against the database.
     * 
     * @param identifiant The administrator's ID
     * @param motDePasse The administrator's password
     * @return true if the credentials are valid, false otherwise
     */
    public boolean verifierMotDePasseAdmin(String identifiant, String motDePasse) {
        // Database connection variables
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";

        // SQL query to verify ID and password
        String sql = "SELECT mot_de_passe FROM administrateur WHERE identifiant = ?";

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(url, utilisateurBDD, motDePasseBDD);
             PreparedStatement cursor = conn.prepareStatement(sql)) {
            
            cursor.setString(1, identifiant);
            
            // Execute query and get results
            try (ResultSet rs = cursor.executeQuery()) {
                if (rs.next()) {
                    String motDePasseStocke = rs.getString("mot_de_passe");

                    // Compare entered password with stored password
                    if (motDePasseStocke.equals(motDePasse)) {
                        // Password is valid
                        return true; 
                    } else {
                        // Password is invalid
                        return false; 
                    }
                } else {
                    // ID not found in database
                    return false; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error during connection or query execution
        }
    }
    
    /**
     * Handles the administrator login button click event.
     * Verifies credentials and navigates to the administrator page if valid.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void ConnexionBoutonAdministrateur(ActionEvent event) {
        String identifiant = codePrive.getText();
        String motDePasse = motDePasseAdministrateur.getText();
    
        if (verifierMotDePasseAdmin(identifiant, motDePasse)) {
            main.afficherPageAdministrateur();
        }
        else {
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Connection Failed");
            alerte.setHeaderText("Connection could not be established");
            alerte.setContentText("The provided credentials are incorrect!");
            alerte.showAndWait();
        }
    }
    
    /**
     * Handles the return button click event.
     * Returns to the home page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void retourBoutonPageConnexionUtilisateur(ActionEvent event) {
        if (main != null) {
            main.afficherAccueil();
        } else {
            System.err.println("ERROR: main is null!");
        }
    }
    
    /**
     * Sets the reference to the main application.
     * 
     * @param main The main application instance
     */
    public void setMain(Main main) {
        this.main = main;
    }
}
