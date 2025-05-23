package com.cytech.fenetres;

import javafx.fxml.FXML;
 
import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
 
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import java.io.ByteArrayInputStream;

/**
 * Controller for displaying user profile information.
 * Handles the display of user details including personal information and identification documents.
 */
public class AfficherProfilUtilisateurController {
    /** The private code associated with the user */
    private int codePrive;

    /** The genealogical tree associated with the user */
    private ArbreGenealogique arbre;
     
    /** Reference to the main application */
    private Main main;
	
    /** Label for displaying the user's last name */
    @FXML
    private Label nomBDD;
    
    /** Label for displaying the user's first name */
    @FXML
    private Label prenomBDD;
    
    /** Label for displaying the user's birth date */
    @FXML
    private Label dateNaissanceValue;
    
    /** Label for displaying the user's nationality */
    @FXML
    private Label nationaliteBDD;
    
    /** Label for displaying the user's social security number */
    @FXML
    private Label NumeroSecuBDD;
    
    /** Label for displaying the user's email address */
    @FXML
    private Label emailBDD;
    
    /** Label for displaying the user's address */
    @FXML
    private Label adresseBDD;
    
    /** Label for displaying the user's phone number */
    @FXML
    private Label numeroTelephoneBDD;
    
    /** ImageView for displaying the user's digital photo */
    @FXML
    private ImageView photoNumerique;
    
    /** ImageView for displaying the user's ID card */
    @FXML
    private ImageView photoIdentite;
    
    /** Database username */
    private static final String utilisateur = "user";
    
    /** Database password */
    private static final String motDePasse = "Password123!";
    
    /** Database connection URL */
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    
    /**
     * Loads the user profile with the specified private code and genealogical tree.
     * 
     * @param codePrive The private code of the user
     * @param arbre The genealogical tree associated with the user
     */
    public void chargerProfil(int codePrive, ArbreGenealogique arbre) {
        this.codePrive = codePrive;
        this.arbre = arbre;
        afficherInformationsUtilisateur(codePrive, arbre);
    }
    
    /**
     * Initializes the controller.
     * Called after the FXML file has been loaded.
     */
    public void initialize() {
    }

    /**
     * Displays the user's information retrieved from the database.
     * 
     * @param codePrive The private code of the user
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherInformationsUtilisateur(int codePrive, ArbreGenealogique arbre) {
        initialize();
        String sql = "SELECT nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, carte_identite " +
                "FROM utilisateur WHERE code_prive = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codePrive);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomBDD.setText(rs.getString("nom"));
                prenomBDD.setText(rs.getString("prenom"));
                dateNaissanceValue.setText(rs.getString("date_naissance"));
                nationaliteBDD.setText(rs.getString("nationalite"));
                NumeroSecuBDD.setText(rs.getString("numero_securite_sociale"));
                emailBDD.setText(rs.getString("email"));
                adresseBDD.setText(rs.getString("adresse"));
                numeroTelephoneBDD.setText(rs.getString("telephone"));

                byte[] photoBytes = rs.getBytes("photo");
                byte[] carteIdentiteBytes = rs.getBytes("carte_identite");

                afficherImage(photoBytes, photoNumerique);
                afficherImage(carteIdentiteBytes, photoIdentite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the private code for the user.
     * 
     * @param codePrive The private code to be set
     */
    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
    }
    
    /**
     * Displays an image in the specified ImageView.
     * 
     * @param imageData The byte array containing the image data
     * @param imageView The ImageView where the image should be displayed
     */
    private static void afficherImage(byte[] imageData, ImageView imageView) {
        if (imageData != null && imageData.length > 0) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            imageView.setImage(image);
        }
    }
    
    /**
     * Sets the genealogical tree.
     * 
     * @param arbre The genealogical tree to be set
     */
    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }
	
    /**
     * Sets the reference to the main application.
     * 
     * @param main The main application instance
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Handles the return button click event.
     * Returns to the main user page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void BoutonRetourPagePrincipaleUtilisateur(ActionEvent event) {
        main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
    }

    /**
     * Handles the modify profile button click event.
     * Navigates to the profile modification page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void modifierProfil(ActionEvent event) {
        main.afficherModifierProfilUtilisateurController(codePrive, arbre);
    }
}
