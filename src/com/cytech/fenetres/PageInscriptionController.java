package com.cytech.fenetres;

import javafx.fxml.FXML;
 
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.cytech.Main;
 


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
    
    public void insertionDemandeBaseDonnes(String nom, String prenom, String email, String adresse, String nationalite, Date dateNaissance, String telephone, String numeroSS, String pieceIdentite, String photoNumerique) {
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique"; // Remplacez par l'URL de votre base de données
         

        // Requête SQL pour insérer les données
        String sql = "INSERT INTO demande_adhesion (nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, carte_identite) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            cursor.setBytes(10, carteIdentiteBytes);

            // Exécution de la requête
            cursor.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Inscription réussie");
            alert.setHeaderText("L'inscription a été effectuée avec succès");
            alert.setContentText("Un administrateur se charge de vérifier vos information. Vous pouvez maintenant retourner à la page d'accueil.");
            alert.showAndWait();
            effacerInformationChamp();
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion dans la base de données.");
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
    		
    		insertionDemandeBaseDonnes(nom, prenom, email, adresse, nationalite, dateNaissance, telephone, numeroSS, pieceIdentite, photoNumerique);
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
		if (!verifierMailUnique(champEmail.getText())) {
			return false;
		}
		
		 
		
		return true;
	}
	public boolean verifierMailUnique(String mail) {
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
    // Event Listener on Button.onAction
    @FXML
    public void retourBoutonPageInscription(ActionEvent event) throws IOException  {
        if (main != null) {
            main.afficherAccueil(); // Utilise ta méthode centralisée
        } else {
            System.err.println("ERREUR : main vaut null !");
        }
    }
    
    @FXML
	private void handleInscriptionButton() 
	{
	}
    
}
