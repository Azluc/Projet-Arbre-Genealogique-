package com.cytech.fenetres;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import java.time.ZoneId;
import java.util.Date;

import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.Cote;
import com.cytech.classeProjet.Genre;
import com.cytech.classeProjet.TypeRelation;
import com.cytech.Main;
import com.cytech.classeProjet.Personne;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

/**
 * Controller for the person addition form.
 * Handles the addition of new persons to the genealogical tree, including validation of relationships
 * and age differences between related persons.
 */
public class FormulaireAjoutPersonneController {
    /** Text field for entering the person's first name */
    @FXML
    private TextField champPrenom;
    
    /** Text field for entering the person's last name */
    @FXML
    private TextField champNom;
    
    /** Date picker for selecting the person's birth date */
    @FXML
    private DatePicker champDateNaissance;
    
    /** Date picker for selecting the person's death date (optional) */
    @FXML
    private DatePicker champDateDeces;
    
    /** Radio button for selecting male gender */
    @FXML
    private RadioButton genreH;
    
    /** Toggle group for gender selection */
    @FXML
    private ToggleGroup GenreGroupe;           
    
    /** Radio button for selecting female gender */
    @FXML
    private RadioButton genreF;
    
    /** Radio button for selecting parent relationship */
    @FXML
    private RadioButton relationParent;
    
    /** Toggle group for relationship type selection */
    @FXML
    private ToggleGroup TypeRelationChoix;    
    
    /** Radio button for selecting sibling relationship */
    @FXML
    private RadioButton relationFS;
    
    /** Radio button for selecting child relationship */
    @FXML
    private RadioButton relationEnfant;

    /** The genealogical tree being modified */
    private ArbreGenealogique arbre;
    
    /** The reference person for establishing relationships */
    private Personne personneReference;
    
    /** The private code associated with the tree */
    private int codePrive;
    
    /** Reference to the main application */
    private Main main;

    /**
     * Gets the genealogical tree.
     * 
     * @return The current genealogical tree
     */
    public ArbreGenealogique getArbre() {
        return arbre;
    }

    /**
     * Sets the reference person for establishing relationships.
     * 
     * @param personne The person to be used as reference
     */
    public void setPersonneReference(Personne personne) {
        this.personneReference = personne;
    }

    /**
     * Sets the private code for the tree.
     * 
     * @param codePrive The private code to be set
     */
    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
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
     * Sets the genealogical tree.
     * 
     * @param arbre The genealogical tree to be set
     */
    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }

    /**
     * Clears all input fields in the form.
     */
    public void effacerInformationChamp() {
        champNom.clear();
        champPrenom.clear();
        champDateNaissance.setValue(null);
        champDateDeces.setValue(null);
        GenreGroupe.selectToggle(null);
        TypeRelationChoix.selectToggle(null);
    }

    /**
     * Handles the validation button click event.
     * Validates the input, creates a new person, and adds them to the tree if all validations pass.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void BoutonValider(ActionEvent event) {
        if (entreeEstValide()) {
            String nom = champNom.getText();
            String prenom = champPrenom.getText();
            Date dateNaissance = Date.from(champDateNaissance.getValue().atStartOfDay(ZoneId.of("Europe/Paris")).toInstant());
            Date dateDeces = champDateDeces.getValue() != null
                    ? Date.from(champDateDeces.getValue().atStartOfDay(ZoneId.of("Europe/Paris")).toInstant())
                    : null;

            RadioButton boutonGenre = (RadioButton) GenreGroupe.getSelectedToggle();
            Genre genre = Genre.valueOf(boutonGenre.getText().toUpperCase());

            Personne nouvellePersonne = new Personne(nom, prenom, dateNaissance, dateDeces, genre, codePrive, 0);
             
            String relation = relationParent.isSelected() ? "parent" : relationEnfant.isSelected() ? "enfant" : "frere_soeur";

            boolean personneValide = verifierPersonneValide(nouvellePersonne, arbre);
            if (personneValide) {
                boolean differenceAgeValide = ageValide(relation, nouvellePersonne, personneReference);
                
                if (differenceAgeValide) {
                    TypeRelation typeRelation = obtenirTypeRelation(relation);
                    arbre.getPersonnes().add(nouvellePersonne);
                    
                    String nomRelation = ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, nouvellePersonne);
                     
                    ajouterLien(nouvellePersonne, personneReference, typeRelation, nomRelation);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initOwner(main.getPrimaryStage());
                    alert.setTitle("Addition successful");
                    alert.setHeaderText("Addition confirmation");
                    alert.setContentText("The person has been successfully added to your genealogical tree.");
                    alert.showAndWait();
                    effacerInformationChamp();
                } else {
                    Alert alerte = new Alert(AlertType.ERROR);
                    alerte.initOwner(main.getPrimaryStage());
                    alerte.setTitle("Addition failed");
                    alerte.setHeaderText("Invalid age difference");
                    alerte.setContentText("The addition failed because the age difference is not valid.");
                    alerte.showAndWait();
                }
            } else {
                Alert alerte = new Alert(AlertType.ERROR);
                alerte.initOwner(main.getPrimaryStage());
                alerte.setTitle("Addition failed");
                alerte.setHeaderText("Addition failed");
                alerte.setContentText("The addition failed. Please check the entered values.");
                alerte.showAndWait();
            }
        } else {
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Addition failed");
            alerte.setHeaderText("Addition failed");
            alerte.setContentText("Please fill in all required fields.");
            alerte.showAndWait();
        }
    }

    /**
     * Verifies if a person can be added to the tree.
     * Checks for duplicates and date coherence.
     * 
     * @param nouvellePersonne The person to be verified
     * @param arbre The genealogical tree
     * @return true if the person can be added, false otherwise
     */
    public boolean verifierPersonneValide(Personne nouvellePersonne, ArbreGenealogique arbre) {
        boolean existeDeja = arbre.getPersonnes().stream().anyMatch(p -> p.estIdentique(nouvellePersonne));

        if (existeDeja) {
            System.out.println("Error: This person already exists in the tree. Operation cancelled.");
            return false;
        }
        if (!Personne.verifierCoherenceDates(nouvellePersonne.getDateNaissance(), nouvellePersonne.getDateDeces())) {
            return false;
        }
        return true;
    }

    /**
     * Validates the age difference between two related persons.
     * 
     * @param relationChoisie The type of relationship being established
     * @param nouvelle The new person being added
     * @param reference The reference person
     * @return true if the age difference is valid, false otherwise
     */
    public boolean ageValide(String relationChoisie, Personne nouvelle, Personne reference) {
        switch (relationChoisie) {
            case "parent":
                if (Personne.aAuMoins18AnsDePlus(nouvelle.getDateNaissance(), reference.getDateNaissance())) {
                    nouvelle.setProfondeur(reference.getProfondeur() + 1);
                    nouvelle.ajouterEnfant(reference, arbre);
                    reference.ajouterParent(nouvelle);
                    return true;
                } else {
                    return false;
                }
            case "enfant":
                if (Personne.aAuMoins18AnsDePlus(reference.getDateNaissance(), nouvelle.getDateNaissance())) {
                    nouvelle.setProfondeur(reference.getProfondeur() - 1);
                    reference.ajouterEnfant(nouvelle, arbre);
                    nouvelle.ajouterParent(reference);
                    nouvelle.setCote(Cote.NEUTRE);
                    return true;
                } else {
                    return false;
                }
            case "frere_soeur":
                nouvelle.setProfondeur(reference.getProfondeur());
                reference.ajouterFrereOuSoeur(nouvelle, arbre);
                nouvelle.setCote(Cote.NEUTRE);
                return true;
            default:
                return false;
        }
    }

    /**
     * Gets the type of relationship based on the selected relationship.
     * 
     * @param relationChoisie The selected relationship type
     * @return The corresponding TypeRelation enum value
     */
    public TypeRelation obtenirTypeRelation(String relationChoisie) {
        if (relationChoisie.equals("parent")) {
            return com.cytech.classeProjet.TypeRelation.PARENT_ENFANT;
        } else if (relationChoisie.equals("enfant")) {
            return com.cytech.classeProjet.TypeRelation.PARENT_ENFANT;
        } else {
            return com.cytech.classeProjet.TypeRelation.FRERE_SOEUR;
        }
    }

    /**
     * Adds a relationship link between two persons in the tree.
     * 
     * @param nouvelle The new person
     * @param reference The reference person
     * @param typeRelation The type of relationship
     * @param nomRelation The name of the relationship
     */
    public void ajouterLien(Personne nouvelle, Personne reference, TypeRelation typeRelation, String nomRelation) {
        if (typeRelation == com.cytech.classeProjet.TypeRelation.PARENT_ENFANT) {
            if (reference.getDateNaissance().after(nouvelle.getDateNaissance())) {
                arbre.getLiensParente().ajouterLien(nouvelle, reference, typeRelation, nomRelation);
            } else {
                arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
            }
        } else {
            arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
        }
        for (Personne p : arbre.getPersonnes()) {
            p.detecterFreresEtSoeurs(arbre.getPersonnes(), arbre);
        }
    }

    /**
     * Validates that all required fields have been filled.
     * 
     * @return true if all required fields are filled, false otherwise
     */
    private boolean entreeEstValide() {
        return champNom.getText() != null && !champNom.getText().trim().isEmpty()
                && champPrenom.getText() != null && !champPrenom.getText().trim().isEmpty()
                && champDateNaissance.getValue() != null
                && GenreGroupe.getSelectedToggle() != null
                && TypeRelationChoix.getSelectedToggle() != null;
    }

    /**
     * Handles the return button click event.
     * Returns to the dropdown list page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void boutonRetour(ActionEvent event) {
        if (main != null) {
            main.afficherPageAjoutListeDeroulanteController(codePrive, arbre);
        } else {
            System.err.println("ERROR: main is null!");
        }
    }
}
