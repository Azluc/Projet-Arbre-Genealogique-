package com.cytech.fenetres;

import javafx.fxml.FXML;


import javafx.scene.control.TextField;

import java.util.List;

import com.cytech.Main;
import com.cytech.gestionBDD.GestionUtilisateurBDD;

import javafx.event.ActionEvent;



import javafx.scene.control.Button;

 

public class PageConnexionUtilisateurController {
	
     
    private Main main;
	@FXML
	private TextField codePrive;
	@FXML
	private TextField motDePasseUtilisateur;
	@FXML
	private Button boutonRetour;

    // Event Listener on Button.onAction
    @FXML
    public void ConnexionBoutonUtilisateur(ActionEvent event) {
        int codePriveFourni = Integer.parseInt(codePrive.getText());
        String motDePasseUtilisateurFourni = motDePasseUtilisateur.getText();
        System.out.println(codePriveFourni + " et voici le mot de passe en bdd");
        List<Object> listeLiens = GestionUtilisateurBDD.getPrenomEtMotDePasseParCodePrive(codePriveFourni);

        if (listeLiens.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé pour ce code privé");
            // Ici tu peux aussi afficher une alerte JavaFX pour prévenir l’utilisateur
            return;
        }
         
        String prenomBDD = (String) listeLiens.get(0);
        String motDePasseBDD = (String) listeLiens.get(1);
        int codePriveBDD = Integer.parseInt(listeLiens.get(2).toString());
 
        
        if ((motDePasseBDD.equals("-1")) ) {
            if ( codePriveFourni== codePriveBDD && prenomBDD.equals(motDePasseUtilisateurFourni) ) {
                System.out.print("première connexion");
                main.afficherPageChangementMDP(codePriveBDD);
            }
            else {
            	System.out.print("erreur identifiant : Premiere Connexion");
            }
        } 
        else {
            if (codePriveFourni== codePriveBDD && motDePasseBDD.equals(motDePasseUtilisateurFourni)) {
            	main.afficherPagePrincipaleUtilisateurController(codePriveBDD);
                System.out.print("seconde connexion");
            }
            else {
            	System.out.print("erreur identifiant : Seconde Connexion");
            }
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
