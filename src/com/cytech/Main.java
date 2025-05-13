package com.cytech;

import java.io.IOException;

import com.cytech.fenetres.PageAccueilController;
import com.cytech.fenetres.PageConnexionAdministrateurController;
import com.cytech.fenetres.PageConnexionUtilisateurController;
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
	
	
	
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Accueil");

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
	
	/*@Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("fenetres/PageAccueil.fxml"));
            Scene scene = new Scene(root);

            

            primaryStage.setTitle("Arbre Généalogique");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
    
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
    
    public void afficherAdministrateurPageConnexion() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("fenetres/PageConnexionAdministrateur.fxml"));
    	try {
			AnchorPane connexion = (AnchorPane) loader.load();
			
			PageConnexionAdministrateurController controller = loader.getController();
			controller.setMain(this);
			
			 
			rootLayout.setCenter(connexion);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
    

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
