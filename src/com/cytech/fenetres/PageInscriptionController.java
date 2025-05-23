package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import com.cytech.Main;
import com.cytech.gestionBDD.GestionDemandeAdhesionBdd;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controller for the registration page.
 * Handles user registration, form validation, and navigation.
 */
public class PageInscriptionController {
    /** Database username */
    public static String utilisateur = "user";
    /** Database password */
    public static String motDePasse = "Password123!";
    /** Reference to the main application */
    private Main main;
    
    @FXML
    private TextField champPrenom;
    @FXML
    private TextField champNom;
    @FXML
    private TextField champEmail;
    @FXML
    private TextField champNationalite;
    @FXML
    private DatePicker champDateNaissance;
    @FXML
    private TextField champAdresse;
    @FXML
    private TextField champTelephone;
    @FXML
    private TextField champNumeroSS;
    @FXML
    private TextField lienPhotoNumerique;
    @FXML
    private TextField lienPieceIdentite;
    @FXML
    private Button boutonRetour;

    /**
     * Opens a file chooser dialog to select a digital photo file.
     * Sets the selected file path in the corresponding text field.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void choisirPhotoNumerique(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        // Open the dialog
        Stage stage = (Stage) lienPhotoNumerique.getScene().getWindow();
        File fichierSelectionne = fileChooser.showOpenDialog(stage);
        // Display the path if a file is chosen
        if (fichierSelectionne != null) {
            lienPhotoNumerique.setText(fichierSelectionne.getAbsolutePath());
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

    /**
     * Opens a file chooser dialog to select an identity document file.
     * Sets the selected file path in the corresponding text field.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void choisirPieceIdentite(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        Stage stage = (Stage) lienPieceIdentite.getScene().getWindow();
        File fichierSelectionne = fileChooser.showOpenDialog(stage);
        // Display the path if a file is chosen
        if (fichierSelectionne != null) {
            lienPieceIdentite.setText(fichierSelectionne.getAbsolutePath());
        }
    }
    
    /**
     * Clears all user input fields in the registration form.
     */
    public void effacerInformationChamp() {
        champNom.clear();
        champDateNaissance.setValue(null);
        champPrenom.clear();
        champEmail.clear();
        champAdresse.clear();
        champNationalite.clear();
        champTelephone.clear();
        champNumeroSS.clear();
        lienPieceIdentite.clear();
        lienPhotoNumerique.clear();
    }
    
    /**
     * Handles the registration confirmation button click event.
     * Validates the input and submits the registration if valid.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void confirmationBoutonInscription(ActionEvent event) {
        if(entreeEstValide()) {
            String nom = champNom.getText();
            String prenom = champPrenom.getText();
            String email = champEmail.getText();
            String adresse = champAdresse.getText();
            String nationalite = champNationalite.getText();
            Date dateNaissance = Date.from(champDateNaissance.getValue().atStartOfDay(ZoneId.of("Europe/Paris")).toInstant());  
            String telephone = champTelephone.getText();
            String numeroSS = champNumeroSS.getText();
            String pieceIdentite = lienPieceIdentite.getText();
            String photoNumerique = lienPhotoNumerique.getText();
            GestionDemandeAdhesionBdd.insertionDemandeBaseDonnes(nom, prenom, email, adresse, nationalite, dateNaissance, telephone, numeroSS, pieceIdentite, photoNumerique);
            effacerInformationChamp();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Registration successful");
            alert.setHeaderText("Registration was completed successfully");
            alert.setContentText("An administrator will verify your information. You can now return to the home page.");
            alert.showAndWait();
        }
        else {
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Registration failed");
            alerte.setHeaderText("Registration could not be completed");
            alerte.setContentText("Please fill in all fields and/or the email address is already used");
            alerte.showAndWait();
        }
    }
    
    /**
     * Validates the user input fields.
     * Checks for empty fields and unique email address.
     * 
     * @return true if all fields are valid and email is unique, false otherwise
     */
    private boolean entreeEstValide() {
        if (champNom.getText() == null || champNom.getText().length() == 0 ||
            champPrenom.getText()  == null || champPrenom.getText().length() == 0 ||
            champEmail.getText()  == null || champEmail.getText().length() == 0 ||
            champNationalite.getText()  == null || champNationalite.getText().length() == 0 ||
            champDateNaissance.getValue()  == null|| 
            champAdresse.getText()  == null || champAdresse.getText().length() == 0 ||
            champTelephone.getText()  == null || champTelephone.getText().length() == 0 ||
            champNumeroSS.getText()  == null || champNumeroSS.getText().length() == 0 ||  
            lienPieceIdentite.getText()  == null || lienPieceIdentite.getText().length() == 0 ||
            lienPhotoNumerique.getText()  == null|| lienPhotoNumerique.getText().length() == 0) {
            return false;
        }
        else {
            // TODO: An entry is not valid if the entered email is already in the database
        }
        if (!GestionDemandeAdhesionBdd.verifierMailUnique(champEmail.getText())) {
            return false;
        }
        return true;
    }
    
    /**
     * Handles the return button click event.
     * Returns to the home page.
     * 
     * @param event The action event that triggered this method
     * @throws IOException if an error occurs during navigation
     */
    @FXML
    public void retourBoutonPageInscription(ActionEvent event) throws IOException  {
        if (main != null) {
            main.afficherAccueil();  
        } else {
            System.err.println("ERROR: main is null!");
        }
    }
    
    /**
     * Handles the registration button click event (currently empty).
     */
    @FXML
    private void handleInscriptionButton() 
    {
    }
    
}
