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

public class PageAdministrateurController {
    private static final String utilisateur = "user";
    private static final String motDePasse ="Password123!";
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    @FXML
    private Button deconnectionAdminID;

    @SuppressWarnings("unused")
	private PageInformationUtilisateurController pageInformationController;

    private Main main;
    @FXML
    private TableView<ObservableList<String>> adherentTable;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColonne;
    @FXML
    private TableColumn<ObservableList<String>, String> prenomColonne;
    @FXML
    private TableColumn<ObservableList<String>, String> EmailColonne;

    @FXML
    private void initialize() {
        // Configure les colonnes
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

    @FXML
    public void BoutonActualiserDemandes(ActionEvent event) {
        actualiserListe();
    }

    public void setMain(Main main, PageInformationUtilisateurController pageInformationController) {
        this.main = main;
        this.pageInformationController = pageInformationController;
    }


    // Event Listener on Button[#deconnectionAdmin].onAction
    @FXML
    public void deconnectionAdmin(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Information");
        alert.setHeaderText("Vous vous êtes bien déconnecter !");
        alert.setContentText("Retour à la page principale.");
        alert.showAndWait();
        main.afficherAccueil();
    }
	
	 
}
