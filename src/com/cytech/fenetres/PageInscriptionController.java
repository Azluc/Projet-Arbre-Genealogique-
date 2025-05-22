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


 

public class PageInscriptionController {
    public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
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

    // Event Listener on Button.onAction
    @FXML
    public void choisirPhotoNumerique(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");



        // Ouvre la boîte de dialogue
        Stage stage = (Stage) lienPhotoNumerique.getScene().getWindow();
        File fichierSelectionne = fileChooser.showOpenDialog(stage);

        // Affiche le chemin si un fichier est choisi
        if (fichierSelectionne != null) {
            lienPhotoNumerique.setText(fichierSelectionne.getAbsolutePath());
        }
    }

    public void setMain(Main main) {
        this.main = main;

    }

    // Event Listener on Button.onAction
    @FXML
    public void choisirPieceIdentite(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");


        Stage stage = (Stage) lienPieceIdentite.getScene().getWindow();
        File fichierSelectionne = fileChooser.showOpenDialog(stage);

        // Affiche le chemin si un fichier est choisi
        if (fichierSelectionne != null) {
            lienPieceIdentite.setText(fichierSelectionne.getAbsolutePath());
        }
    }
 
    
    public void effacerInformationChamp() {
    	// efface les champs de texte saisie par l'utilisateur
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
    
    // Event Listener on Button.onAction
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
            alert.setTitle("Inscription réussie");
            alert.setHeaderText("L'inscription a été effectuée avec succès");
            alert.setContentText("Un administrateur se charge de vérifier vos information. Vous pouvez maintenant retourner à la page d'accueil.");
            alert.showAndWait();
		}
		else {
			Alert alerte = new Alert(AlertType.ERROR);
			alerte.initOwner(main.getPrimaryStage());
			alerte.setTitle("Inscription impossible");
			alerte.setHeaderText("L'inscription n'a pu aboutir");
			alerte.setContentText("Veuillez remplir tout les champs et/ou adresse mail déja utilisée");
			alerte.showAndWait();
		}
	}
	
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
			/*
			 * 
			 * A FAIRE, une entrée n'est pas valide si le mail entrée est déjà dans la bdd
			 * */
		}
		if (!GestionDemandeAdhesionBdd.verifierMailUnique(champEmail.getText())) {
			return false;
		}
		
		 
		
		return true;
	}
	 
    // Event Listener on Button.onAction
    @FXML
    public void retourBoutonPageInscription(ActionEvent event) throws IOException  {
        if (main != null) {
            main.afficherAccueil();  
        } else {
            System.err.println("ERREUR : main vaut null !");
        }
    }
    
    @FXML
	private void handleInscriptionButton() 
	{
	}
    
}
