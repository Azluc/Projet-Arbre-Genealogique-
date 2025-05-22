package com.cytech.fenetres;

import javafx.fxml.FXML;

import javax.swing.JFrame;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.ArbreGenealogiquePanel;

import javafx.event.ActionEvent;

public class PagePrincipaleUtilisateurController {
	
	private int codePrive;
	private Main main;
	private ArbreGenealogique arbre;
	
	public void setMain(Main main) {
        this.main = main;

    }
	
	
	public void setArbre(ArbreGenealogique arbre) {
	    this.arbre = arbre;
	}
	
	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void modifierProfilUtilisateur(ActionEvent event) {
		main.afficherProfilUtilisateurController(codePrive,arbre);
	}
	// Event Listener on Button.onAction
	@FXML
	public void DeconnectionUtilisateur(ActionEvent event) {
		main.afficherAccueil();
	}
	// Event Listener on Button.onAction
	@FXML
	public void ajouterPersonneArbreUtilisateur(ActionEvent event) {
		main.afficherPageAjoutListeDeroulanteController(codePrive,arbre);
	}
	// Event Listener on Button.onAction

	// Event Listener on Button.onAction
	@FXML
	public void genererArbreGraphiquement(ActionEvent event) {
        JFrame frame = new JFrame("Arbre généalogique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ArbreGenealogiquePanel(arbre));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
	}
	// Event Listener on Button.onAction
	@FXML
	public void genererArbreConsole(ActionEvent event) {
		System.out.println("\nListe des liens de parenté dans l'arbre :");
		ArbreGenealogique.afficherRelations(arbre);
	}
	// Event Listener on Button.onAction
	@FXML
	public void faireDesRequetesArbresUtilisateur(ActionEvent event) {
		main.afficherPageRequetesController(codePrive, arbre);
	}

	
 
}
