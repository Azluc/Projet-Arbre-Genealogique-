package com.cytech.fenetres;

import javafx.fxml.FXML;


import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import com.cytech.Main;
import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.LienParente;
import com.cytech.classeProjet.Personne;
import com.cytech.gestionBDD.GestionArbreGenealogiqueBDD;
import com.cytech.gestionBDD.GestionUtilisateurBDD;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

 

public class PageConnexionUtilisateurController {
	
     
    private Main main;
	@FXML
	private TextField codePrive;
	@FXML
	private TextField motDePasseUtilisateur;
	@FXML
	private Button boutonRetour;

	public void messageErreur() {
    	Alert alerte = new Alert(AlertType.ERROR);
		alerte.initOwner(main.getPrimaryStage());
		alerte.setTitle("Connexion impossible");
		alerte.setHeaderText("La connexion n'a pu aboutir");
		alerte.setContentText("Les identifiants saisis sont incorrect !");
		alerte.showAndWait();
	}
    // Event Listener on Button.onAction
	@FXML
	public void ConnexionBoutonUtilisateur(ActionEvent event) throws SQLException {
	    //int codePriveFourni = Integer.parseInt(codePrive.getText());
	    
	    
	    try {
	    	int codePriveFourni = Integer.parseInt(codePrive.getText());
	    	String motDePasseUtilisateurFourni = motDePasseUtilisateur.getText();
		    List<Object> listeLiens = GestionUtilisateurBDD.getPrenomEtMotDePasseParCodePrive(codePriveFourni);

		    if (listeLiens.isEmpty()) {
		    	messageErreur();
		        return;
		    }

		    String prenomBDD = (String) listeLiens.get(0);
		    String motDePasseBDD = (String) listeLiens.get(1);
		    int codePriveBDD = Integer.parseInt(listeLiens.get(2).toString());

		    if (motDePasseBDD.equals("-1")) {
		        if (codePriveFourni == codePriveBDD && prenomBDD.equals(motDePasseUtilisateurFourni)) {
		            //System.out.print("première connexion");
		            main.afficherPageChangementMDP(codePriveBDD);
		        } else {
		        	messageErreur();
		        }
		    } else {
		        if (codePriveFourni == codePriveBDD && motDePasseBDD.equals(motDePasseUtilisateurFourni)) {
		        	
		        	
			        GestionArbreGenealogiqueBDD.initConnexion();

			        String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePriveBDD);
			        
			        if (prenomRacine == null) {
			            System.out.println("Aucun arbre trouvé pour ce code privé");
			            return;
			        }

			        Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePriveBDD, prenomRacine);
			        if (racine == null) {
			            System.out.println("Personne racine non trouvée dans la base ");
			            return;
			        }

			        ArbreGenealogique arbre = new ArbreGenealogique(racine, codePriveBDD, new HashSet<>());  
			        arbre.getPersonnes().add(racine);
			        arbre.setLiensParente(new LienParente(arbre));
	 
		            main.afficherPagePrincipaleUtilisateurController(codePriveBDD, arbre); //  
		            //System.out.print("seconde connexion");
		        } else {
		        	messageErreur();
		        }
		    }
	    } catch (NumberFormatException e) {
	        System.out.println("Le code doit être un nombre entier valide.");
	        messageErreur();
	        return;
	    }
	     
	}
    // Event Listener on Button.onAction
    @FXML
    public void boutonInscription(ActionEvent event) {
        main.afficherPageInscription();
    }
    public void setMain(Main main) {
        this.main = main;

    }
	// Event Listener on Button[#boutonRetour].onAction
	@FXML
	public void retourBoutonPageConnexionUtilisateur(ActionEvent event) {
		if (main != null) {
            main.afficherAccueil(); // Utilise ta méthode centralisée
        } else {
            System.err.println("ERREUR : main vaut null !");
        }
	}
}
