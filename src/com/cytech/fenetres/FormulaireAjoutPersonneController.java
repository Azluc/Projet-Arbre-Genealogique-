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

public class FormulaireAjoutPersonneController {
    @FXML
    private TextField champPrenom;
    @FXML
    private TextField champNom;
    @FXML
    private DatePicker champDateNaissance;
    @FXML
    private DatePicker champDateDeces;
    @FXML
    private RadioButton genreH;
    @FXML
    private ToggleGroup GenreGroupe;           
    @FXML
    private RadioButton genreF;
    @FXML
    private RadioButton relationParent;
    @FXML
    private ToggleGroup TypeRelationChoix;    
    @FXML
    private RadioButton relationFS;
    @FXML
    private RadioButton relationEnfant;

    private ArbreGenealogique arbre;
    private Personne personneReference;
    private int codePrive;
    private Main main;

    public ArbreGenealogique getArbre() {
        return arbre;
    }

    public void setPersonneReference(Personne personne) {
        this.personneReference = personne;
    }

    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setArbre(ArbreGenealogique arbre) {
        this.arbre = arbre;
    }

    public void effacerInformationChamp() {
        champNom.clear();
        champPrenom.clear();
        champDateNaissance.setValue(null);
        champDateDeces.setValue(null);
        GenreGroupe.selectToggle(null);
        TypeRelationChoix.selectToggle(null);
    }

    // Event Listener on Button.onAction
    @FXML
    public void BoutonValider(ActionEvent event) {
        if (entreeEstValide()) {

            String nom = champNom.getText();
            String prenom = champPrenom.getText();
            Date dateNaissance = Date.from(champDateNaissance.getValue().atStartOfDay(ZoneId.of("Europe/Paris")).toInstant());
            Date dateDeces = champDateDeces.getValue() != null
                    ? Date.from(champDateDeces.getValue().atStartOfDay(ZoneId.of("Europe/Paris")).toInstant())
                    : null;

            // Création de la personne
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
                    alert.setTitle("Ajout réussie");
                    alert.setHeaderText("Confirmation d'ajout");
                    alert.setContentText("L'ajout de la personne dans votre arbre généalogique a été réussie.");
                    alert.showAndWait();
                    effacerInformationChamp();

                } else {
                    Alert alerte = new Alert(AlertType.ERROR);
                    alerte.initOwner(main.getPrimaryStage());
                    alerte.setTitle("Ajout échouée");
                    alerte.setHeaderText("différence d'âge incorrect");
                    alerte.setContentText("L'ajout a échoué, la différence d'âge inscrite n'est pas correcte.");
                    alerte.showAndWait();
                }

            } else {
                Alert alerte = new Alert(AlertType.ERROR);
                alerte.initOwner(main.getPrimaryStage());
                alerte.setTitle("Ajout échouée");
                alerte.setHeaderText("Ajout échoué");
                alerte.setContentText("L'ajout a échoué, veuillez vérifier les valeurs remplies.");
                alerte.showAndWait();
            }
        } else {
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Ajout échouée");
            alerte.setHeaderText("Ajout échoué");
            alerte.setContentText("Veuillez remplir tous les champs.");
            alerte.showAndWait();
        }
    }

    public boolean verifierPersonneValide(Personne nouvellePersonne, ArbreGenealogique arbre) {
        boolean existeDeja = arbre.getPersonnes().stream().anyMatch(p -> p.estIdentique(nouvellePersonne));

        if (existeDeja) {
            System.out.println("Erreur : cette personne existe déjà dans l'arbre. Opération annulée.");
            return false;
        }
        if (!Personne.verifierCoherenceDates(nouvellePersonne.getDateNaissance(), nouvellePersonne.getDateDeces())) {
            return false;
        }
        return true;
    }

    public boolean ageValide(String relationChoisie, Personne nouvelle, Personne reference) {
        switch (relationChoisie) {
            case "parent":
                if (Personne.aAuMoins18AnsDePlus(nouvelle.getDateNaissance(), reference.getDateNaissance())) {
                    nouvelle.setProfondeur(reference.getProfondeur() + 1);
                    nouvelle.ajouterEnfant(reference, arbre);
                    reference.ajouterParent(nouvelle);
                    return true;
                } else {
                    // Le parent doit être plus âgé que l’enfant.
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
                    // L’enfant doit être plus jeune que le parent.
                    return false;
                }
            case "frere_soeur":

                nouvelle.setProfondeur(reference.getProfondeur());
                reference.ajouterFrereOuSoeur(nouvelle, arbre); // ajoute la nouvellePersonne en tant que frere
               
                nouvelle.setCote(Cote.NEUTRE);
                return true;

            default:
                return false;

        }
    }

    public TypeRelation obtenirTypeRelation(String relationChoisie) {

        if (relationChoisie.equals("parent")) {
            return com.cytech.classeProjet.TypeRelation.PARENT_ENFANT;
        } else if (relationChoisie.equals("enfant")) {
            return com.cytech.classeProjet.TypeRelation.PARENT_ENFANT;
        } else {
            return com.cytech.classeProjet.TypeRelation.FRERE_SOEUR;
        }
    }

    public void ajouterLien(Personne nouvelle, Personne reference, TypeRelation typeRelation, String nomRelation) {
        if (typeRelation == com.cytech.classeProjet.TypeRelation.PARENT_ENFANT) {
            // Assurer que la personne source est le parent et destination est l'enfant
            if (reference.getDateNaissance().after(nouvelle.getDateNaissance())) {
                // reference est plus jeune que nouvelle, donc on inverse
                arbre.getLiensParente().ajouterLien(nouvelle, reference, typeRelation, nomRelation);
                /*
                 *
                 * Rajoute une methode qui va ajouter dans la table Parent_Enfant
                 * */
            } else {
                arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
                /*
                 *
                 * Pareil ici
                 * */
            }
        } else {
            // Autres types (FRERE_SOEUR, UNION) : on garde l’ordre normal
            arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
            /*
             * Pareil ici, mais avec la table Frere_Soeur
             *
             * */
        }
        for (Personne p : arbre.getPersonnes()) {
            p.detecterFreresEtSoeurs(arbre.getPersonnes(), arbre);
        }
    }

    private boolean entreeEstValide() {

        return champNom.getText() != null && !champNom.getText().trim().isEmpty()
                && champPrenom.getText() != null && !champPrenom.getText().trim().isEmpty()
                && champDateNaissance.getValue() != null
                && GenreGroupe.getSelectedToggle() != null
                && TypeRelationChoix.getSelectedToggle() != null;
    }

    // Event Listener on Button.onAction
    @FXML
    public void boutonRetour(ActionEvent event) {
        if (main != null) {
            main.afficherPageAjoutListeDeroulanteController(codePrive, arbre);
        } else {
            System.err.println("ERREUR : main vaut null !");
        }
    }
}
