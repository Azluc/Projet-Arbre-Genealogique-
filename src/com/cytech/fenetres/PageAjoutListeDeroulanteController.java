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

public class PageAjoutListeDeroulanteController {
    @SuppressWarnings("unused")
	private static final String utilisateur = "user";
    @SuppressWarnings("unused")
	private static final String motDePasse = "Password123!";
    @SuppressWarnings("unused")
	private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";

    @FXML
    private ComboBox<Personne> maListeDeroulante;

    @FXML
    private TableView<Personne> adherentTable;
    @FXML
    private TableColumn<Personne, String> nomColonne;
    @FXML
    private TableColumn<Personne, String> prenomColonne;
    @FXML
    private TableColumn<Personne, String> RelationColonne;
    @FXML
    private TableColumn<Personne, String> NaissanceColonne;
    @FXML
    private TableColumn<Personne, String> mortColonne;
    
    @FXML
    private Button boutonRetour;

    private int codePrive;
    private Main main;
    private ArbreGenealogique arbre;
    
    @FXML
    private void initialize() {
        // Configuration des colonnes du tableau
        nomColonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        RelationColonne.setCellValueFactory(cellData -> {
            // Cette méthode retourne le type de relation - peut être adapté selon votre besoin
            return new SimpleStringProperty(ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, cellData.getValue()));
        });
        NaissanceColonne.setCellValueFactory(cellData -> {
            Date naissance = cellData.getValue().getDateNaissance();
            return new SimpleStringProperty(naissance != null ? naissance.toString() : "");
        });
        mortColonne.setCellValueFactory(cellData -> {
            Date deces = cellData.getValue().getDateDeces();
            return new SimpleStringProperty(deces != null ? deces.toString() : "Vivant");
        });
        
        // Configuration de la liste déroulante pour afficher nom et prénom
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
                // Cette méthode est nécessaire pour le StringConverter mais n'est pas utilisée ici
                return null;
            }
        });
        
        // Ajout d'un listener pour la sélection dans la liste déroulante
        maListeDeroulante.setOnAction(event -> {
            Personne personneSelectionnee = maListeDeroulante.getSelectionModel().getSelectedItem();
            if (personneSelectionnee != null) {
                main.afficherFormulaireAjoutPersonneController(personneSelectionnee, codePrive, arbre);
            }
        });
        
        // Ajout d'un listener pour la sélection dans le tableau
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
     * Charge les profils de personnes dans la ComboBox et le TableView
     */
    public void chargerProfil(int codePrive) {
        this.codePrive = codePrive;
        
        try {
            // Initialiser la connexion à la BDD
            GestionArbreGenealogiqueBDD.initConnexion();
            
            // Charger l'arbre généalogique depuis la BDD
            GestionArbreGenealogiqueBDD.chargerArbreDepuisBDD(codePrive);
            
            // Récupérer le prénom de la racine
            String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePrive);
            if (prenomRacine == null) {
                throw new SQLException("Impossible de trouver la racine de l'arbre");
            }
            
            // Récupérer la personne racine
            Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePrive, prenomRacine);
            if (racine == null) {
                throw new SQLException("Impossible de trouver la personne racine dans la BDD");
            }
            
            // Récupérer toutes les personnes
            Set<Personne> personnesSet = new HashSet<>(GestionArbreGenealogiqueBDD.getPersonnes());
            
            // Créer ou mettre à jour l'arbre avec les données chargées
            if (arbre == null) {
                arbre = new ArbreGenealogique(racine, codePrive, personnesSet);
            } else {
                arbre.setRacine(racine);
                arbre.setId_arbre(codePrive);
                arbre.setPersonnes(personnesSet);
            }
            
            // Mise à jour de la liste déroulante et du tableau
            ObservableList<Personne> listePersonnes = FXCollections.observableArrayList(personnesSet);
            maListeDeroulante.setItems(listePersonnes);
            adherentTable.setItems(listePersonnes);
            
            // Debuggage
            //System.out.println("Arbre chargé avec " + personnesSet.size() + " personnes");
            //System.out.println("Racine: " + racine.getPrenom() + " " + racine.getNom());
            
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger les données");
            alert.setContentText("Une erreur est survenue lors du chargement des données: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
     // Pour actualiser les donneés
    @FXML
    public void actualiserDonnees() {
        try {
            // Recharger l'arbre depuis la BDD
            GestionArbreGenealogiqueBDD.chargerArbreDepuisBDD(codePrive);
            
            // Mettre à jour l'arbre avec les nouvelles données
            Set<Personne> personnesSet = new HashSet<>(GestionArbreGenealogiqueBDD.getPersonnes());
            arbre.setPersonnes(personnesSet);
            
            // Mise à jour de l'interface
            ObservableList<Personne> listePersonnes = FXCollections.observableArrayList(personnesSet);
            maListeDeroulante.setItems(listePersonnes);
            adherentTable.setItems(listePersonnes);
            
            System.out.println("Données actualisées - " + personnesSet.size() + " personnes");
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur d'actualisation");
            alert.setHeaderText("Impossible d'actualiser les données");
            alert.setContentText("Une erreur est survenue: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Méthode appelée quand le bouton retour est cliqué
     */
    @FXML
    public void boutonRetour(ActionEvent event) {
        if (main != null) {
            main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);  
        } else {
            System.err.println("ERREUR : main vaut null !");
        }
    }
    
    // Setters
    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }
    
    public void setMain(Main main) {
        this.main = main;
    }
    
    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
    }
}