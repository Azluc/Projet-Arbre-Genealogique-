package com.cytech.fenetres;

import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.HashSet;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.LienParente;
import com.cytech.classeProjet.Personne;
import com.cytech.gestionBDD.GestionArbreGenealogiqueBDD;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

/**
 * Controller for the password change page.
 * Handles the process of changing a user's password and updating related database information.
 */
public class PageChangementMDPController {
	/** Reference to the main application */
	private Main main;

	/** Password field for entering the new password */
	@FXML
	private PasswordField motDePasseChanger;

	/** The private code associated with the user */
	private int codePrive;

	/** The genealogical tree associated with the user */
	@SuppressWarnings("unused")
	private ArbreGenealogique arbre;

	/**
	 * Sets the private code for the user.
	 * 
	 * @param codePrive The private code to be set
	 */
	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
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
	 * Handles the password change validation.
	 * Updates the user's password in the database and reloads the genealogical tree.
	 * 
	 * @param event The action event that triggered this method
	 * @throws SQLException If a database error occurs during the update
	 */
	@FXML
	public void ValiderChangementMDP(ActionEvent event) throws SQLException {
		String nouveauMotDePasse = motDePasseChanger.getText();
		boolean success = com.cytech.gestionBDD.GestionUtilisateurBDD.modifierMotDePasseParCodePrive(codePrive, nouveauMotDePasse);
		GestionArbreGenealogiqueBDD.initConnexion();

		String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePrive);
		if (prenomRacine == null) {
			System.out.println("No tree found for this private code");
			return;
		}

		Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePrive, prenomRacine);
		if (racine == null) {
			System.out.println("Root person not found in database");
			return;
		}

		ArbreGenealogique arbre = new ArbreGenealogique(racine, codePrive, new HashSet<>());  
		arbre.getPersonnes().add(racine);
		arbre.setLiensParente(new LienParente(arbre));
		if (success) {
			System.out.println("Password updated successfully!");
			main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
		} else {
			System.out.println("Update failed: private code not found.");
		}
	}
	
	/**
	 * Sets the reference to the main application.
	 * 
	 * @param main The main application instance
	 */
	public void setMain(Main main) {
		this.main = main;
	}
}