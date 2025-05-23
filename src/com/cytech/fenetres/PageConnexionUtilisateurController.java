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

/**
 * Controller for the user login page.
 * Handles user authentication and navigation to the main user interface or password change page for first-time users.
 */
public class PageConnexionUtilisateurController {
    
    /** Reference to the main application */
    private Main main;
    
    /** Text field for entering private code */
    @FXML
    private TextField codePrive;
    
    /** Text field for entering password */
    @FXML
    private TextField motDePasseUtilisateur;
    
    /** Button to return to previous page */
    @FXML
    private Button boutonRetour;

    /**
     * Displays an error message when login fails.
     */
    public void messageErreur() {
        Alert alerte = new Alert(AlertType.ERROR);
        alerte.initOwner(main.getPrimaryStage());
        alerte.setTitle("Connection Failed");
        alerte.setHeaderText("Connection could not be established");
        alerte.setContentText("The provided credentials are incorrect!");
        alerte.showAndWait();
    }

    /**
     * Handles the user login button click event.
     * Verifies user credentials and either:
     * - Redirects to password change page for first-time users
     * - Loads the user's genealogical tree and shows the main user interface
     * 
     * @param event The action event that triggered this method
     * @throws SQLException If a database error occurs
     */
    @FXML
    public void ConnexionBoutonUtilisateur(ActionEvent event) throws SQLException {
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
                    main.afficherPageChangementMDP(codePriveBDD);
                } else {
                    messageErreur();
                }
            } else {
                if (codePriveFourni == codePriveBDD && motDePasseBDD.equals(motDePasseUtilisateurFourni)) {
                    GestionArbreGenealogiqueBDD.initConnexion();

                    String prenomRacine = GestionArbreGenealogiqueBDD.getPrenomRacine(codePriveBDD);
                    
                    if (prenomRacine == null) {
                        System.out.println("No tree found for this private code");
                        return;
                    }

                    Personne racine = GestionArbreGenealogiqueBDD.getPersonneRacine(codePriveBDD, prenomRacine);
                    if (racine == null) {
                        System.out.println("Root person not found in database");
                        return;
                    }

                    ArbreGenealogique arbre = new ArbreGenealogique(racine, codePriveBDD, new HashSet<>());  
                    arbre.getPersonnes().add(racine);
                    arbre.setLiensParente(new LienParente(arbre));
     
                    main.afficherPagePrincipaleUtilisateurController(codePriveBDD, arbre);
                } else {
                    messageErreur();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("The code must be a valid integer.");
            messageErreur();
            return;
        }
    }

    /**
     * Handles the registration button click event.
     * Navigates to the registration page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void boutonInscription(ActionEvent event) {
        main.afficherPageInscription();
    }

    /**
     * Sets the reference to the main application.
     * 
     * @param main The main application instance
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Handles the return button click event.
     * Returns to the home page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void retourBoutonPageConnexionUtilisateur(ActionEvent event) {
        if (main != null) {
            main.afficherAccueil();
        } else {
            System.err.println("ERROR: main is null!");
        }
    }
}
