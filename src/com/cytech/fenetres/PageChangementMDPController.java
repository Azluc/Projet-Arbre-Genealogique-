package com.cytech.fenetres;

import javafx.fxml.FXML;

import com.cytech.Main;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

public class PageChangementMDPController {

	@SuppressWarnings("unused")
	private Main main;

	@FXML
	private PasswordField motDePasseChanger;

	private int codePrive;

	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
	}

	@FXML
	public void ValiderChangementMDP(ActionEvent event) {
		String nouveauMotDePasse = motDePasseChanger.getText();
		boolean success = com.cytech.gestionBDD.GestionUtilisateurBDD.modifierMotDePasseParCodePrive(codePrive, nouveauMotDePasse);

		if (success) {
			System.out.println("Mot de passe mis à jour avec succès !");
			main.afficherPagePrincipaleUtilisateurController(codePrive);
			// Tu peux ajouter une alerte JavaFX ici pour prévenir l'utilisateur
		} else {
			System.out.println("Échec de la mise à jour : code privé introuvable.");
		}
	}
	
	   public void setMain(Main main) {
	        this.main = main;

	    }
}