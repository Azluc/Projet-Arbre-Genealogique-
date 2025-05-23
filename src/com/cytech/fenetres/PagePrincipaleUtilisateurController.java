package com.cytech.fenetres;

import javafx.fxml.FXML;

import javax.swing.JFrame;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.ArbreGenealogiquePanel;

import javafx.event.ActionEvent;

/**
 * Controller for the main user page.
 * Handles user interactions with the genealogical tree, including viewing, modifying,
 * and generating different representations of the tree.
 */
public class PagePrincipaleUtilisateurController {
	/** The private code associated with the user */
	private int codePrive;
	
	/** Reference to the main application */
	private Main main;
	
	/** The genealogical tree associated with the user */
	private ArbreGenealogique arbre;
	
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
	 * Sets the private code for the user.
	 * 
	 * @param codePrive The private code to be set
	 */
	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
	}
	
	/**
	 * Handles the button click to modify user profile.
	 * Navigates to the profile modification page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void modifierProfilUtilisateur(ActionEvent event) {
		main.afficherProfilUtilisateurController(codePrive, arbre);
	}
	
	/**
	 * Handles the button click to disconnect the user.
	 * Returns to the home page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void DeconnectionUtilisateur(ActionEvent event) {
		main.afficherAccueil();
	}
	
	/**
	 * Handles the button click to add a person to the genealogical tree.
	 * Navigates to the person addition page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void ajouterPersonneArbreUtilisateur(ActionEvent event) {
		main.afficherPageAjoutListeDeroulanteController(codePrive, arbre);
	}
	
	/**
	 * Handles the button click to generate a graphical representation of the genealogical tree.
	 * Creates and displays a new window with the tree visualization.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void genererArbreGraphiquement(ActionEvent event) {
		JFrame frame = new JFrame("Genealogical Tree");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ArbreGenealogiquePanel(arbre));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Handles the button click to generate a console representation of the genealogical tree.
	 * Displays the family relationships in the console.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void genererArbreConsole(ActionEvent event) {
		System.out.println("\nList of family relationships in the tree:");
		ArbreGenealogique.afficherRelations(arbre);
	}
	
	/**
	 * Handles the button click to perform queries on the genealogical tree.
	 * Navigates to the queries page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void faireDesRequetesArbresUtilisateur(ActionEvent event) {
		main.afficherPageRequetesController(codePrive, arbre);
	}
}
