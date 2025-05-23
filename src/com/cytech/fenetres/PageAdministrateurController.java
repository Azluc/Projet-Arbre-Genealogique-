package com.cytech.fenetres;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Button; 
import com.cytech.Main;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

/**
 * Controller for the administrator page.
 * Handles the display and management of user registration requests,
 * including viewing user details and accepting/rejecting requests.
 */
public class PageAdministrateurController {
    /** Database username */
    private static final String utilisateur = "user";
    
    /** Database password */
    private static final String motDePasse = "Password123!";
    
    /** Database connection URL */
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    
    /** Button for administrator logout */
    @FXML
    private Button deconnectionAdminID;

    /** Controller for user information display */
    @SuppressWarnings("unused")
    private PageInformationUtilisateurController pageInformationController;

    /** Reference to the main application */
    private Main main;
    
    /** Table view for displaying user registration requests */
    @FXML
    private TableView<ObservableList<String>> adherentTable;
    
    /** Column for displaying last names */
    @FXML
    private TableColumn<ObservableList<String>, String> nomColonne;
    
    /** Column for displaying first names */
    @FXML
    private TableColumn<ObservableList<String>, String> prenomColonne;
    
    /** Column for displaying email addresses */
    @FXML
    private TableColumn<ObservableList<String>, String> EmailColonne;

    /**
     * Initializes the controller.
     * Sets up the table columns and loads the initial data.
     */
    @FXML
    private void initialize() {
        // Configure columns
        nomColonne.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        prenomColonne.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        EmailColonne.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        actualiserListe();

        adherentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ObservableList<String> ligne = adherentTable.getSelectionModel().getSelectedItem();
                if (ligne != null && ligne.size() >= 3) {
                    String email = ligne.get(2);
                    main.afficherPageInformationUtilisateur(email);
                }
            }
        });
    }

    /**
     * Updates the list of registration requests.
     * Retrieves and displays the latest data from the database.
     */
    private void actualiserListe() {
        ObservableList<ObservableList<String>> donnees = FXCollections.observableArrayList();

        String sql = "SELECT nom, prenom, email FROM demande_adhesion";
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ObservableList<String> ligne = FXCollections.observableArrayList();
                ligne.add(rs.getString("nom"));
                ligne.add(rs.getString("prenom"));
                ligne.add(rs.getString("email"));
                donnees.add(ligne);
            }

            adherentTable.setItems(donnees);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the refresh button click event.
     * Updates the list of registration requests.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void BoutonActualiserDemandes(ActionEvent event) {
        actualiserListe();
    }

    /**
     * Sets the main application and user information controller.
     * 
     * @param main The main application instance
     * @param pageInformationController The controller for user information display
     */
    public void setMain(Main main, PageInformationUtilisateurController pageInformationController) {
        this.main = main;
        this.pageInformationController = pageInformationController;
    }

    /**
     * Handles the administrator logout button click event.
     * Returns to the home page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void deconnectionAdmin(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Information");
        alert.setHeaderText("You have been successfully logged out!");
        alert.setContentText("Returning to the main page.");
        alert.showAndWait();
        main.afficherAccueil();
    }
}
