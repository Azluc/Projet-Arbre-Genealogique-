package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.sql.SQLException;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;

import com.cytech.classeProjet.Personne;
import com.cytech.gestionBDD.GestionArbreGenealogiqueBDD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import javafx.util.StringConverter;

/**
 * Controller for the person addition page with dropdown list.
 * Handles the display and selection of persons in the genealogical tree,
 * allowing users to add new persons and establish relationships.
 */
public class PageAjoutListeDeroulanteController {
    /** Database username */
    @SuppressWarnings("unused")
    private static final String utilisateur = "user";
    
    /** Database password */
    @SuppressWarnings("unused")
    private static final String motDePasse = "Password123!";
    
    /** Database connection URL */
    @SuppressWarnings("unused")
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";

    /** Dropdown list for selecting persons */
    @FXML
    private ComboBox<Personne> maListeDeroulante;

    /** Table view for displaying persons */
    @FXML
    private TableView<Personne> adherentTable;
    
    /** Column for displaying last names */
    @FXML
    private TableColumn<Personne, String> nomColonne;
    
    /** Column for displaying first names */
    @FXML
    private TableColumn<Personne, String> prenomColonne;
    
    /** Column for displaying relationships */
    @FXML
    private TableColumn<Personne, String> RelationColonne;
    
    /** Column for displaying birth dates */
    @FXML
    private TableColumn<Personne, String> NaissanceColonne;
    
    /** Column for displaying death dates */
    @FXML
    private TableColumn<Personne, String> mortColonne;
    
    /** Button to return to the previous page */
    @FXML
    private Button boutonRetour;

    /** The private code associated with the user */
    private int codePrive;
    
    /** Reference to the main application */
    private Main main;
    
    /** The genealogical tree associated with the user */
    private ArbreGenealogique arbre;
    
    /**
     * Initializes the controller.
     * Sets up the table columns and dropdown list configuration.
     */
    @FXML
    private void initialize() {
        // Configure table columns
        nomColonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        RelationColonne.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, cellData.getValue()));
        });
        NaissanceColonne.setCellValueFactory(cellData -> {
            Date naissance = cellData.getValue().getDateNaissance();
            return new SimpleStringProperty(naissance != null ? naissance.toString() : "");
        });
        mortColonne.setCellValueFactory(cellData -> {
            Date deces = cellData.getValue().getDateDeces();
            return new SimpleStringProperty(deces != null ? deces.toString() : "Alive");
        });
        
        // Configure dropdown list to display name and surname
        maListeDeroulante.setConverter(new StringConverter<Personne>() {
            @Override
            public String toString(Personne personne) {
                if (personne == null) {
                    return "";
                }
                return personne.getPrenom() + " " + personne.getNom();
            }

            @Override
            public Personne fromString(String string) {
                return null;
            }
        });
        
        // Add listener for dropdown selection
        maListeDeroulante.setOnAction(event -> {
            Personne personneSelectionnee = maListeDeroulante.getSelectionModel().getSelectedItem();
            if (personneSelectionnee != null) {
                main.afficherFormulaireAjoutPersonneController(personneSelectionnee, codePrive, arbre);
            }
        });
        
        // Add listener for table selection
        adherentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Personne personneSelectionnee = adherentTable.getSelectionModel().getSelectedItem();
                if (personneSelectionnee != null) {
                    maListeDeroulante.getSelectionModel().select(personneSelectionnee);
                }
            } else if (event.getClickCount() == 2) {
                Personne personneSelectionnee = adherentTable.getSelectionModel().getSelectedItem();
                if (personneSelectionnee != null) {
                    main.afficherFormulaireAjoutPersonneController(personneSelectionnee, codePrive, arbre);
                }
            }
        });
    }
    
    /**
     * Loads the user profile and initializes the genealogical tree.
     * 
     * @param codePrive The private code of the user
     */
    public void chargerProfil(int codePrive) {
        this.codePrive = codePrive;
        
        try {
            // Initialize database connection
            GestionArbreGenealogiqueBDD.initConnexion();
            
            // Load genealogical tree from database
            GestionArbreGenealogiqueBDD.chargerArbreDepuisBDD(codePrive);
            
            // Get root person's first name
            String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePrive);
            if (prenomRacine == null) {
                throw new SQLException("Unable to find tree root");
            }
            
            // Get root person
            Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePrive, prenomRacine);
            if (racine == null) {
                throw new SQLException("Unable to find root person in database");
            }
            
            // Get all persons
            Set<Personne> personnesSet = new HashSet<>(GestionArbreGenealogiqueBDD.getPersonnes());
            
            // Create or update tree with loaded data
            if (arbre == null) {
                arbre = new ArbreGenealogique(racine, codePrive, personnesSet);
            } else {
                arbre.setRacine(racine);
                arbre.setId_arbre(codePrive);
                arbre.setPersonnes(personnesSet);
            }
            
            // Update dropdown list and table
            ObservableList<Personne> listePersonnes = FXCollections.observableArrayList(personnesSet);
            maListeDeroulante.setItems(listePersonnes);
            adherentTable.setItems(listePersonnes);
            
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Loading error");
            alert.setHeaderText("Unable to load data");
            alert.setContentText("An error occurred while loading data: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the data by reloading from the database.
     */
    @FXML
    public void actualiserDonnees() {
        try {
            // Reload tree from database
            GestionArbreGenealogiqueBDD.chargerArbreDepuisBDD(codePrive);
            
            // Update tree with new data
            Set<Personne> personnesSet = new HashSet<>(GestionArbreGenealogiqueBDD.getPersonnes());
            arbre.setPersonnes(personnesSet);
            
            // Update interface
            ObservableList<Personne> listePersonnes = FXCollections.observableArrayList(personnesSet);
            maListeDeroulante.setItems(listePersonnes);
            adherentTable.setItems(listePersonnes);
            
            System.out.println("Data updated - " + personnesSet.size() + " persons");
            Alert info = new Alert(AlertType.CONFIRMATION);
            info.initOwner(main.getPrimaryStage());
            info.setTitle("Update successful");
            info.setHeaderText("Data loaded");
            info.setContentText("Your genealogical tree data has been successfully loaded!");
            info.showAndWait();
        
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Update error");
            alert.setHeaderText("Unable to update data");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Handles the return button click event.
     * Returns to the main user page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void boutonRetour(ActionEvent event) {
        if (main != null) {
            main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);  
        } else {
            System.err.println("ERROR: main is null!");
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
     * Sets the private code for the user.
     * 
     * @param codePrive The private code to be set
     */
    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
    }
}