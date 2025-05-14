package com.cytech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cytech.fenetres.PageAccueilController;
import com.cytech.fenetres.PageAdministrateurController;
import com.cytech.fenetres.PageConnexionAdministrateurController;
import com.cytech.fenetres.PageConnexionUtilisateurController;
import com.cytech.fenetres.PageInformationUtilisateurController;
import com.cytech.fenetres.PageInscriptionController;
 

import javafx.application.Application;

 
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    public static  String utilisateur = "user";
    public static String motDePasse ="Password123!";
    
    
	
	
	
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Accueil");
        insererAdministrateur();
        initRootLayout();
        
        afficherAccueil();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/RacinePrincipale.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void afficherAccueil() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/PageAccueil.fxml"));
            AnchorPane accueil = (AnchorPane) loader.load();
            
            PageAccueilController accueilController = loader.getController();
            accueilController.setMain(this);
            // Set person overview into the center of root layout.
            rootLayout.setCenter(accueil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    
    public void afficherUtilisateurPageConnexion() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("fenetres/PageConnexionUtilisateur.fxml"));
    	try {
			AnchorPane connexion = (AnchorPane) loader.load();
			
			PageConnexionUtilisateurController controller = loader.getController();
			controller.setMain(this);
			
			 
			rootLayout.setCenter(connexion);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void afficherPageInformationUtilisateur(String email) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageInformationUtilisateur.fxml"));
        try {
            AnchorPane pageInformationUtilisateur = loader.load();
            PageInformationUtilisateurController controller = loader.getController();
            controller.setMain(this);
            controller.afficherInformationsUtilisateur(email); // <<< Appel ici après le chargement du FXML

            rootLayout.setCenter(pageInformationUtilisateur);
            this.primaryStage.setTitle("Page Administrateur");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void afficherAdministrateurPageConnexion() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("fenetres/PageConnexionAdministrateur.fxml"));
    	try {
			AnchorPane administrateurPageConnexion = (AnchorPane) loader.load();
			
			PageConnexionAdministrateurController controller = loader.getController();
			controller.setMain(this);
			
			 
			rootLayout.setCenter(administrateurPageConnexion);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void afficherPageAdministrateur() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageAdministrateur.fxml"));
        try {
            AnchorPane pageAdministrateur = (AnchorPane) loader.load();
            PageAdministrateurController controller = loader.getController();
            // Crée une instance de PageInformationUtilisateurController
            PageInformationUtilisateurController pageInfoController = new PageInformationUtilisateurController();
            // Passe l'instance de PageInformationUtilisateurController à PageAdministrateurController
            controller.setMain(this, pageInfoController);
            rootLayout.setCenter(pageAdministrateur);
            this.primaryStage.setTitle("Page Administrateur"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void afficherPageInscription() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("fenetres/PageInscription.fxml"));
    	try {
    		AnchorPane inscription = (AnchorPane) loader.load();
    		PageInscriptionController controller = loader.getController();
    		controller.setMain(this);
    		rootLayout.setCenter(inscription);
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void insererAdministrateur(){
		 
		
		String url = "jdbc:mysql://localhost:3306/arbre_genealogique"; // Remplacez par l'URL de votre base de données
        

         
        // Connexion à la base de données
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // Requête d'insertion SQL
            String sql = "INSERT INTO administrateur (identifiant, mot_de_passe) VALUES (?, ?)";

            // Préparation de la requête avec des paramètres
            try (PreparedStatement cursor = conn.prepareStatement(sql)) {
                
            	cursor.setString(1, "admin"); // identifiant de l'utilisateur
            	cursor.setString(2, "admin123"); // mot de passe de l'utilisateur

                // Exécuter la requête d'insertion
                cursor.executeUpdate();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
    

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}