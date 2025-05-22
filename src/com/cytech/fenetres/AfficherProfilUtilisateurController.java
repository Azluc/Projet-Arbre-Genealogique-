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
 

 

public class AfficherProfilUtilisateurController {
	 
	private int codePrive;

	 
	private ArbreGenealogique arbre;
	 
    private Main main;
	
    @FXML
    private Label nomBDD;
    @FXML
    private Label prenomBDD;
    @FXML
    private Label dateNaissanceValue;
    @FXML
    private Label nationaliteBDD;
    @FXML
    private Label NumeroSecuBDD;
    @FXML
    private Label emailBDD;
    @FXML
    private Label adresseBDD;
    @FXML
    private Label numeroTelephoneBDD;
    @FXML
    private ImageView photoNumerique;
    @FXML
    private ImageView photoIdentite;
    
 

    private static final String utilisateur = "user";
    private static final String motDePasse = "Password123!";
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    
 
    public void chargerProfil(int codePrive,ArbreGenealogique arbre) {
        this.codePrive = codePrive;
        this.arbre = arbre;
        afficherInformationsUtilisateur(codePrive,arbre);
    }
    
    
    public void initialize() {

    }
    // Méthode pour afficher les informations utilisateur selon l'email
    public void afficherInformationsUtilisateur(int codePrive,ArbreGenealogique arbre) {
        initialize();
       // System.out.println(codePrive);
        String sql = "SELECT nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, carte_identite " +
                "FROM utilisateur WHERE code_prive = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codePrive);  // Remplace par l'email de l'utilisateur
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               // System.out.println(rs.getString("nom"));
                // Récupérer les valeurs des champs
                nomBDD.setText(rs.getString("nom"));
                prenomBDD.setText(rs.getString("prenom"));
                dateNaissanceValue.setText(rs.getString("date_naissance"));
                nationaliteBDD.setText(rs.getString("nationalite"));
                NumeroSecuBDD.setText(rs.getString("numero_securite_sociale"));
                emailBDD.setText(rs.getString("email"));
                adresseBDD.setText(rs.getString("adresse"));
                numeroTelephoneBDD.setText(rs.getString("telephone"));

                // Charger les images (photo numérique et carte d'identité)
                byte[] photoBytes = rs.getBytes("photo");
                byte[] carteIdentiteBytes = rs.getBytes("carte_identite");

                // Afficher les images dans les ImageView
                afficherImage(photoBytes, photoNumerique);
                afficherImage(carteIdentiteBytes, photoIdentite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
	}
    
 // Méthode pour afficher l'image dans le ImageView
    private static void afficherImage(byte[] imageData, ImageView imageView) {
        if (imageData != null && imageData.length > 0) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            imageView.setImage(image);
        }
    }
    
    public void setArbre(ArbreGenealogique arbre) {
	    this.arbre = arbre;
	}
	
	public void setMain(Main main) {
	    this.main = main;
	}
	// Event Listener on Button.onAction
	@FXML
	public void BoutonRetourPagePrincipaleUtilisateur(ActionEvent event) {
		main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
	}
	// Event Listener on Button.onAction
	@FXML
	public void modifierProfil(ActionEvent event) {
		main.afficherModifierProfilUtilisateurController(codePrive,arbre);
	}
}
