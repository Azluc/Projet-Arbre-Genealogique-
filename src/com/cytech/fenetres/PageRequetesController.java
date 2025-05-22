package com.cytech.fenetres;

import javafx.fxml.FXML;
import java.util.Set;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.gestionBDD.GestionLienParenteBDD;

import javafx.event.ActionEvent;

public class PageRequetesController {

	private int codePrive;
	private ArbreGenealogique arbre;
	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public void setArbre(ArbreGenealogique arbre) {
		this.arbre = arbre;
	}

	public ArbreGenealogique getArbre() {
		return arbre;
	}

	public void chargerProfil(int codePrive, ArbreGenealogique arbre) {
		this.codePrive = codePrive;
		this.arbre = arbre;
	}

	@FXML
	public void boutonEnVie(ActionEvent event) {
	    Set<String> vivants = GestionLienParenteBDD.recupererPersonnesVivantes(codePrive);

	    if (vivants.isEmpty()) {
	        System.out.println("Aucune personne vivante trouvée.");
	    } else {
	        System.out.println("Liste des personnes vivantes :");
	        for (String prenom : vivants) {
	            System.out.println("- " + prenom);
	        }
	    }
	}

	@FXML
	public void boutonDecede(ActionEvent event) {
		Set<String> decedes = GestionLienParenteBDD.recupererPersonnesDecedees(codePrive);

	    if (decedes.isEmpty()) {
	        System.out.println("Aucune personne décédée trouvée.");
	    } else {
	        System.out.println("Liste des personnes décédées :");
	        for (String prenom : decedes) {
	            System.out.println("- " + prenom);
	        }
	    }
	}

	@FXML
	public void boutonFemme(ActionEvent event) {
	    Set<String> femmes = GestionLienParenteBDD.recupererFemmes(codePrive);

	    if (femmes.isEmpty()) {
	        System.out.println("Aucune femme trouvée dans l'arbre.");
	    } else {
	        System.out.println("Liste des femmes :");
	        for (String prenom : femmes) {
	            System.out.println("- " + prenom);
	        }
	    }
	}

	@FXML
	public void boutonHomme(ActionEvent event) {
	    Set<String> hommes = GestionLienParenteBDD.recupererHommes(codePrive);

	    if (hommes.isEmpty()) {
	        System.out.println("Aucun homme trouvé dans l'arbre.");
	    } else {
	        System.out.println("Liste des hommes :");
	        for (String prenom : hommes) {
	            System.out.println("- " + prenom);
	        }
	    }
	}

	@FXML
	public void boutonRetour(ActionEvent event) {
		main.afficherPagePrincipaleUtilisateurController(codePrive, arbre);
	}
}
