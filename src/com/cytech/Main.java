package com.cytech;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.Personne;
import com.cytech.fenetres.AfficherProfilUtilisateurController;
import com.cytech.fenetres.FormulaireAjoutPersonneController;
import com.cytech.fenetres.ModifierProfilUtilisateurController;
import com.cytech.fenetres.PageAccueilController;
import com.cytech.fenetres.PageAdministrateurController;
import com.cytech.fenetres.PageAjoutListeDeroulanteController;
import com.cytech.fenetres.PageChangementMDPController;
import com.cytech.fenetres.PageConnexionAdministrateurController;
import com.cytech.fenetres.PageConnexionUtilisateurController;
import com.cytech.fenetres.PageInformationUtilisateurController;
import com.cytech.fenetres.PageInscriptionController;
import com.cytech.fenetres.PagePrincipaleUtilisateurController;
import com.cytech.fenetres.PageRequetesController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class of the genealogical tree application.
 * This class manages the initialization and navigation between different windows of the application.
 * It inherits from the JavaFX Application class to handle the graphical interface.
 */
public class Main extends Application {
    /** The main window of the application */
    private Stage primaryStage;
    
    /** The root layout of the application */
    private BorderPane rootLayout;
    
    /** Default username */
    public static String utilisateur = "user";
    
    /** Default password */
    public static String motDePasse = "Password123!";
    
    /**
     * Main entry point of the application.
     * Initializes the main window and displays the home page.
     * 
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Home");
        insererAdministrateur();
        initRootLayout();
        
        afficherAccueil();
    }
    
    /**
     * Initializes the root layout of the application.
     * Loads the RacinePrincipale.fxml file and configures the main scene.
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
     * Displays the home page in the root layout.
     * Loads and configures the home page with its controller.
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
    
    /**
     * Displays the user login page.
     * Loads and configures the login page with its controller.
     */
    public void afficherUtilisateurPageConnexion() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageConnexionUtilisateur.fxml"));
        try {
            AnchorPane connexion = (AnchorPane) loader.load();
            
            PageConnexionUtilisateurController controller = loader.getController();
            controller.setMain(this);
            
            rootLayout.setCenter(connexion);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Displays the user information page.
     * 
     * @param email The email of the user whose information to display
     */
    public void afficherPageInformationUtilisateur(String email) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageInformationUtilisateur.fxml"));
        try {
            AnchorPane pageInformationUtilisateur = loader.load();
            PageInformationUtilisateurController controller = loader.getController();
            controller.setMain(this);
            controller.afficherInformationsUtilisateur(email);

            rootLayout.setCenter(pageInformationUtilisateur);
            this.primaryStage.setTitle("Administrator Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the administrator login page.
     * Loads and configures the administrator login page with its controller.
     */
    public void afficherAdministrateurPageConnexion() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageConnexionAdministrateur.fxml"));
        try {
            AnchorPane administrateurPageConnexion = (AnchorPane) loader.load();
            
            PageConnexionAdministrateurController controller = loader.getController();
            controller.setMain(this);
            
            rootLayout.setCenter(administrateurPageConnexion);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Displays the administrator page.
     * Loads and configures the administrator page with its controller.
     */
    public void afficherPageAdministrateur() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageAdministrateur.fxml"));
        try {
            AnchorPane pageAdministrateur = (AnchorPane) loader.load();
            PageAdministrateurController controller = loader.getController();
            // Create an instance of PageInformationUtilisateurController
            PageInformationUtilisateurController pageInfoController = new PageInformationUtilisateurController();
            // Pass the PageInformationUtilisateurController instance to PageAdministrateurController
            controller.setMain(this, pageInfoController);
            rootLayout.setCenter(pageAdministrateur);
            this.primaryStage.setTitle("Administrator Page"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the password change page.
     * 
     * @param codePrive The user's private code
     */
    public void afficherPageChangementMDP(int codePrive) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fenetres/PageChangementMDP.fxml"));
        try {
            AnchorPane page = loader.load();

            // Get the FXML controller
            PageChangementMDPController controller = loader.getController();

            // Inject Main (important!)
            controller.setMain(this);

            // Inject the private code into the controller
            controller.setCodePrive(codePrive);

            // Display the page
            rootLayout.setCenter(page);
            this.primaryStage.setTitle("Change Password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the main user page.
     * 
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherPagePrincipaleUtilisateurController(int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/PagePrincipaleUtilisateur.fxml"));
            AnchorPane page = loader.load();

            PagePrincipaleUtilisateurController controller = loader.getController();
            controller.setMain(this);
            controller.setCodePrive(codePrive);
            controller.setArbre(arbre); // Pass the tree

            rootLayout.setCenter(page);
            primaryStage.setTitle("Main User Page");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user profile.
     * 
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherProfilUtilisateurController(int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/AfficherProfilUtilisateur.fxml"));
            AnchorPane page = loader.load();

            AfficherProfilUtilisateurController controller = loader.getController();
            // Call the method to load and display info with codePrive
            controller.chargerProfil(codePrive,arbre);
            controller.setMain(this);

            rootLayout.setCenter(page);
            primaryStage.setTitle("Your Profile");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user profile modification page.
     * 
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherModifierProfilUtilisateurController(int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/ModifierProfilUtilisateur.fxml"));
            AnchorPane page = loader.load();

            ModifierProfilUtilisateurController controller = loader.getController();
            // Call the method to load and display info with codePrive
            controller.chargerProfil(codePrive);     // Load private code
            controller.chargerArbre(arbre);          // Load tree
            controller.setMain(this);                // Set main

            rootLayout.setCenter(page);
            primaryStage.setTitle("Modify Your Profile");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the dropdown list addition page.
     * 
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherPageAjoutListeDeroulanteController(int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/PageAjoutListeDeroulante.fxml"));
            AnchorPane page = loader.load();

            PageAjoutListeDeroulanteController controller = loader.getController();
            controller.setArbre(arbre); 
            controller.chargerProfil(codePrive);
            controller.setMain(this);  

            rootLayout.setCenter(page);
            primaryStage.setTitle("Add Person");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the queries page.
     * 
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherPageRequetesController(int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/PageRequetes.fxml"));
            AnchorPane page = loader.load();

            PageRequetesController controller = loader.getController();
            controller.chargerProfil(codePrive,arbre);  
            controller.setMain(this);
            rootLayout.setCenter(page);
            primaryStage.setTitle("Queries Page");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the person addition form.
     * 
     * @param personne The person to add
     * @param codePrive The user's private code
     * @param arbre The genealogical tree associated with the user
     */
    public void afficherFormulaireAjoutPersonneController(Personne personne, int codePrive, ArbreGenealogique arbre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fenetres/FormulaireAjoutPersonne.fxml"));
            AnchorPane page = loader.load();

            FormulaireAjoutPersonneController controller = loader.getController();
            controller.setMain(this);
            controller.setCodePrive(codePrive);
            controller.setPersonneReference(personne);
            controller.setArbre(arbre); 
            rootLayout.setCenter(page);
            primaryStage.setTitle("Add a Linked Person");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the registration page.
     * Loads and configures the registration page with its controller.
     */
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

    /**
     * Inserts the default administrator into the database.
     * This method is called when the application starts.
     */
    public void insererAdministrateur() {
        String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
        
        // Connect to the database
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // SQL insertion query
            String sql = "INSERT INTO administrateur (identifiant, mot_de_passe) VALUES (?, ?)";

            // Prepare the query with parameters
            try (PreparedStatement cursor = conn.prepareStatement(sql)) {
                cursor.setString(1, "admin"); // user identifier
                cursor.setString(2, "admin123"); // user password

                // Execute the insertion query
                cursor.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main window of the application.
     * 
     * @return The main window (Stage)
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Application entry point.
     * Launches the JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}