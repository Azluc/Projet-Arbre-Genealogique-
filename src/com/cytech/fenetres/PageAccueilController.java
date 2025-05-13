package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import com.cytech.Main;


public class PageAccueilController {

	private Main main;
	// Event Listener on Button.onAction
	
	public PageAccueilController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	public void boutonInscriptionPageAccueil(ActionEvent event) {
		if (main != null) {
            main.afficherPageInscription();
        } else {
            System.out.println("Erreur : main est null");
        }
	}
	// Event Listener on Button.onAction
	@FXML
    public void boutonConnexionUtilisateurPageAccueil(ActionEvent event) {
        if (main != null) {
            main.afficherUtilisateurPageConnexion();
        } else {
            System.out.println("Erreur : main est null");
        }
    }
	// Event Listener on Button.onAction
	@FXML
	public void boutonConnexionAdministrateurPageAccueil(ActionEvent event) {
		 if (main != null) {
	            main.afficherAdministrateurPageConnexion();
	        } else {
	            System.out.println("Erreur : main est null");
	        }
	}
	
	public void setMain(Main main) {
	    this.main = main;
	}
}
