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

public class PageChangementMDPController {

	 
	private Main main;

	@FXML
	private PasswordField motDePasseChanger;

	private int codePrive;

	@SuppressWarnings("unused")
	private ArbreGenealogique arbre;

	public void setCodePrive(int codePrive) {
		this.codePrive = codePrive;
	}

	public void setArbre(ArbreGenealogique arbre) {
	    this.arbre = arbre;
	}
	
	
	@FXML
	public void ValiderChangementMDP(ActionEvent event) throws SQLException {
		String nouveauMotDePasse = motDePasseChanger.getText();
		boolean success = com.cytech.gestionBDD.GestionUtilisateurBDD.modifierMotDePasseParCodePrive(codePrive, nouveauMotDePasse);
		GestionArbreGenealogiqueBDD.initConnexion();

        String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePrive);
        if (prenomRacine == null) {
            System.out.println("Aucun arbre trouvé pour ce code privé");
            return;
        }

        Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePrive, prenomRacine);
        if (racine == null) {
            System.out.println("Personne racine non trouvée dans la base");
            return;
        }

        ArbreGenealogique arbre = new ArbreGenealogique(racine, codePrive, new HashSet<>());  
        arbre.getPersonnes().add(racine);
        arbre.setLiensParente(new LienParente(arbre));
		if (success) {
			System.out.println("Mot de passe mis à jour avec succès !");
			main.afficherPagePrincipaleUtilisateurController(codePrive,arbre);
			 
		} else {
			System.out.println("Échec de la mise à jour : code privé introuvable.");
		}
	}
	
	   public void setMain(Main main) {
	        this.main = main;

	    }
}