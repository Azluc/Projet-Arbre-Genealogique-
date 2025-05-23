package com.cytech.fenetres;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;


import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.gestionBDD.GestionUtilisateurBDD;

import javafx.event.ActionEvent;

 
public class ModifierProfilUtilisateurController {

private ArbreGenealogique arbre;
    private int codePrive;

    private Main main;

    @FXML
    private TextField champEmail;
    @FXML
    private TextField champNationalite;
    @FXML
    private TextField champAdresse;
    @FXML
    private TextField champTelephone;
    @FXML
    private Button boutonRetour;

// Event Listener on Button.onAction
@FXML
public void boutonValider(ActionEvent event) throws SQLException {
String email = champEmail.getText();
        String nationalite = champNationalite.getText();
        String adresse = champAdresse.getText();
        String telephone = champTelephone.getText();
        int resultat = GestionUtilisateurBDD.mettreAJourUtilisateurParCodePrive(codePrive,email,nationalite,adresse,telephone);
        if (resultat==1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Inscription réussie");
            alert.setHeaderText("L'inscription a été effectuée avec succès");
            alert.setContentText("Un administrateur se charge de vérifier vos information. Vous pouvez maintenant retourner à la page d'accueil.");
            alert.showAndWait();
            effacerInformationChamp();

            main.afficherPagePrincipaleUtilisateurController(codePrive,arbre);
        }
        else if(resultat==0){
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Erreur Modification");
            alerte.setHeaderText("Aucune modifications faites");
            alerte.setContentText("Veuillez remplir remplir au moins un champ");
            alerte.showAndWait();
        }
        else {
            Alert alerte = new Alert(AlertType.ERROR);
            alerte.initOwner(main.getPrimaryStage());
            alerte.setTitle("Erreur Modification");
            alerte.setHeaderText("Erreur Modification");
            alerte.setContentText("Erreur dans la modifications des informations, veuillez recommencer");
            alerte.showAndWait();
        }


   
}

public void chargerArbre(ArbreGenealogique arbre) {
   this.arbre = arbre;
}

public void setArbre(ArbreGenealogique arbre) {
   this.arbre = arbre;
}

    public void effacerInformationChamp() {
    // efface les champs de texte saisie par l'utilisateur
 
champEmail.clear();
champAdresse.clear();
champNationalite.clear();
champTelephone.clear();
 
    }

// Event Listener on Button[#boutonRetour].onAction
@FXML
public void retourPagePrincipale(ActionEvent event) {
main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
}

public void chargerProfil(int codePrive) {
        this.codePrive = codePrive;

    }

    public void setCodePrive(int codePrive) {
        this.codePrive = codePrive;
    }

   

    public void setMain(Main main) {
        this.main = main;
    }

}