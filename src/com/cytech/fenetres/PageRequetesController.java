package com.cytech.fenetres;

import javafx.fxml.FXML;
import java.util.Set;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.gestionBDD.GestionLienParenteBDD;

import javafx.event.ActionEvent;

/**
 * Controller for handling genealogical tree queries.
 * Provides functionality to query and display different aspects of the genealogical tree
 * such as living persons, deceased persons, women, and men.
 */
public class PageRequetesController {
	/** The private code associated with the user */
	private int codePrive;
	
	/** The genealogical tree associated with the user */
	private ArbreGenealogique arbre;
	
	/** Reference to the main application */
	private Main main;

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
	 * Gets the current genealogical tree.
	 * 
	 * @return The current genealogical tree
	 */
	public ArbreGenealogique getArbre() {
		return arbre;
	}

	/**
	 * Loads the user profile with the specified private code and genealogical tree.
	 * 
	 * @param codePrive The private code of the user
	 * @param arbre The genealogical tree associated with the user
	 */
	public void chargerProfil(int codePrive, ArbreGenealogique arbre) {
		this.codePrive = codePrive;
		this.arbre = arbre;
	}

	/**
	 * Handles the button click to display living persons in the genealogical tree.
	 * Retrieves and displays a list of all living persons in the tree.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonEnVie(ActionEvent event) {
		Set<String> vivants = GestionLienParenteBDD.recupererPersonnesVivantes(codePrive);

		if (vivants.isEmpty()) {
			System.out.println("No living persons found.");
		} else {
			System.out.println("List of living persons:");
			for (String prenom : vivants) {
				System.out.println("- " + prenom);
			}
		}
	}

	/**
	 * Handles the button click to display deceased persons in the genealogical tree.
	 * Retrieves and displays a list of all deceased persons in the tree.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonDecede(ActionEvent event) {
		Set<String> decedes = GestionLienParenteBDD.recupererPersonnesDecedees(codePrive);

		if (decedes.isEmpty()) {
			System.out.println("No deceased persons found.");
		} else {
			System.out.println("List of deceased persons:");
			for (String prenom : decedes) {
				System.out.println("- " + prenom);
			}
		}
	}

	/**
	 * Handles the button click to display women in the genealogical tree.
	 * Retrieves and displays a list of all women in the tree.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonFemme(ActionEvent event) {
		Set<String> femmes = GestionLienParenteBDD.recupererFemmes(codePrive);

		if (femmes.isEmpty()) {
			System.out.println("No women found in the tree.");
		} else {
			System.out.println("List of women:");
			for (String prenom : femmes) {
				System.out.println("- " + prenom);
			}
		}
	}

	/**
	 * Handles the button click to display men in the genealogical tree.
	 * Retrieves and displays a list of all men in the tree.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonHomme(ActionEvent event) {
		Set<String> hommes = GestionLienParenteBDD.recupererHommes(codePrive);

		if (hommes.isEmpty()) {
			System.out.println("No men found in the tree.");
		} else {
			System.out.println("List of men:");
			for (String prenom : hommes) {
				System.out.println("- " + prenom);
			}
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
		main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
	}
}
