package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import com.cytech.Main;

/**
 * Controller for the home page.
 * Handles navigation to registration, user login, and administrator login pages.
 */
public class PageAccueilController {

	/** Reference to the main application */
	private Main main;
	
	/**
	 * Default constructor.
	 */
	public PageAccueilController() {
	}
	
	/**
	 * Initializes the controller after the FXML file has been loaded.
	 */
	@FXML
	private void initialize() {
	}
	
	/**
	 * Handles the registration button click event.
	 * Navigates to the registration page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonInscriptionPageAccueil(ActionEvent event) {
		if (main != null) {
			main.afficherPageInscription();
		} else {
			System.out.println("Error: main is null");
		}
	}
	
	/**
	 * Handles the user login button click event.
	 * Navigates to the user login page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonConnexionUtilisateurPageAccueil(ActionEvent event) {
		if (main != null) {
			main.afficherUtilisateurPageConnexion();
		} else {
			System.out.println("Error: main is null");
		}
	}
	
	/**
	 * Handles the administrator login button click event.
	 * Navigates to the administrator login page.
	 * 
	 * @param event The action event that triggered this method
	 */
	@FXML
	public void boutonConnexionAdministrateurPageAccueil(ActionEvent event) {
		if (main != null) {
			main.afficherAdministrateurPageConnexion();
		} else {
			System.out.println("Error: main is null");
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