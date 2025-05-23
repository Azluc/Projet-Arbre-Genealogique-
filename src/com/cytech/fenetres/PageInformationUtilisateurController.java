package com.cytech.fenetres;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.cytech.Main;
import com.cytech.classeProjet.Genre;
 
import com.cytech.classeProjet.Personne;
import com.cytech.gestionBDD.GestionArbreGenealogiqueBDD;
import com.cytech.gestionBDD.GestionDemandeAdhesionBdd;
import com.cytech.gestionBDD.GestionPersonneBDD;
import com.cytech.gestionBDD.GestionUtilisateurBDD;
 
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
 
import java.io.ByteArrayInputStream;
 
import java.io.IOException;
 

import jakarta.mail.*;
import jakarta.mail.internet.*;

 
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
 

/**
 * Controller for the user information display page.
 * Handles the display and management of user registration requests,
 * including viewing user details, verifying documents, and accepting/rejecting requests.
 */
public class PageInformationUtilisateurController {
     
    /** Reference to the main application */
    private Main main;

    /** Button to return to the administrator page */
    @FXML
    private Button BoutonRetourPageAdmin;

    /** Label displaying the user's last name */
    @FXML
    private Label nomBDD;

    /** Label displaying the user's first name */
    @FXML
    private Label prenomBDD;

    /** Label displaying the user's birth date */
    @FXML
    private Label dateNaissanceValue;

    /** Label displaying the user's nationality */
    @FXML
    private Label nationaliteBDD;

    /** Label displaying the user's social security number */
    @FXML
    private Label NumeroSecuBDD;

    /** Label displaying the user's email address */
    @FXML
    private Label emailBDD;

    /** Label displaying the user's address */
    @FXML
    private Label adresseBDD;

    /** Label displaying the user's phone number */
    @FXML
    private Label numeroTelephoneBDD;

    /** ImageView for displaying the user's digital photo */
    @FXML
    private ImageView photoNumerique;

    /** ImageView for displaying the user's ID card */
    @FXML
    private ImageView photoIdentite;

    /** Button to accept the registration request */
    @FXML
    private Button boutonAccepter;

    /** Button to reject the registration request */
    @FXML
    private Button boutonRefuser;

    /** Database username */
    private static final String utilisateur = "user";

    /** Database password */
    private static final String motDePasse = "Password123!";

    /** Database connection URL */
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";

    /**
     * Initializes the controller.
     */
    public void initialize() {

    }

    /**
     * Displays user information based on the provided email address.
     * Retrieves and displays user details from the database, including personal information
     * and identification documents.
     * 
     * @param email The email address of the user whose information is to be displayed
     */
    public void afficherInformationsUtilisateur(String email) {
        initialize();
        //System.out.println(email);
        String sql = "SELECT nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, carte_identite " +
                "FROM demande_adhesion WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);  // Remplace par l'email de l'utilisateur
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               // System.out.println(rs.getString("nom"));
                // Récupérer les valeurs des champs
                nomBDD.setText(rs.getString("nom"));
                prenomBDD.setText(rs.getString("prenom"));
                dateNaissanceValue.setText(rs.getString("date_naissance"));
                nationaliteBDD.setText(rs.getString("nationalite"));
                NumeroSecuBDD.setText(rs.getString("numero_securite_sociale"));
                emailBDD.setText(rs.getString("email"));
                adresseBDD.setText(rs.getString("adresse"));
                numeroTelephoneBDD.setText(rs.getString("telephone"));

                // Charger les images (photo numérique et carte d'identité)
                byte[] photoBytes = rs.getBytes("photo");
                byte[] carteIdentiteBytes = rs.getBytes("carte_identite");

                // Afficher les images dans les ImageView
                afficherImage(photoBytes, photoNumerique);
                afficherImage(carteIdentiteBytes, photoIdentite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an image in the specified ImageView.
     * 
     * @param imageData The byte array containing the image data
     * @param imageView The ImageView where the image should be displayed
     */
    private static void afficherImage(byte[] imageData, ImageView imageView) {
        if (imageData != null && imageData.length > 0) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            imageView.setImage(image);
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

    /**
     * Generates a random number between 1 and 1,000,000.
     * Used for generating public and private codes.
     * 
     * @return A random integer between 1 and 1,000,000
     */
    public static int genererNombreAleatoire() {
        Random random = new Random();

        return random.nextInt(1_000_000) + 1;
    }
    
    
     
    
    /**
     * Handles the acceptance of a registration request.
     * Generates public and private codes, sends them via email,
     * and adds the user to the database.
     * 
     * @param event The action event that triggered this method
     * @throws IOException If an I/O error occurs
     * @throws SQLException If a database error occurs
     * @throws ParseException If a date parsing error occurs
     */
    @FXML
    public void boutonAccepterAdhesion(ActionEvent event) throws IOException, SQLException, ParseException {

        //preparation des codes publics et prives
        int codePublic = genererNombreAleatoire();
        int codePrive = genererNombreAleatoire();

        String codePublicToString = Integer.toString(codePublic);
        String codePriveToString = Integer.toString(codePrive);
        
        String expediteur = "administrateur@gmail.com";   
        String destinataire = emailBDD.getText();   

        // Configuration SMTP pour FakeSMTP avec le nouveau port
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");  // FakeSMTP sur localhost
        props.put("mail.smtp.port", "2525");  // Le nouveau port (2525 dans cet exemple)
        props.put("mail.smtp.auth", "false");  // Pas d'authentification

        // Création de la session SMTP
        Session session = Session.getInstance(props);

        try {
            // Création du message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur));  // L'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));  // Le destinataire
            message.setSubject("Envoie des codes public et privees");  // Sujet de l'email
            message.setText("Bonjour,"
                    + "Vous trouverez ci-joint votre code public et votre code privee"
                    + " Code Public : " +codePublicToString
                    + " Code Prive : " +codePriveToString
                    +" Cordialement.");  // Corps du message

            // Envoi du message
            Transport.send(message);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Information");
            alert.setHeaderText("La demande d'adhésion a été validée");
            alert.setContentText("L'utilisateur recevra par mail ses différents codes.");
            alert.showAndWait();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        String nom = nomBDD.getText();
		String prenom = prenomBDD.getText();
		String email = emailBDD.getText();
		String adresse = adresseBDD.getText();
		String nationalite = nationaliteBDD.getText();
		 
		
		String telephone = numeroTelephoneBDD.getText();
		String numeroSS = NumeroSecuBDD.getText();
		
		List<byte[]> listeImages = GestionDemandeAdhesionBdd.getImagesParEmail(email);

		// Récupérer directement les byte[] sans passer par des fichiers
		byte[] photoBytes = null;
		byte[] carteIdentiteBytes = null;

		if (!listeImages.isEmpty()) {
		    photoBytes = listeImages.get(0);
		    carteIdentiteBytes = listeImages.get(1);
		}

		// Appel de la méthode d'ajout avec les byte[] récupérés
		GestionUtilisateurBDD.AjouterUtilisateur(
		    nom,
		    prenom,
		    dateNaissanceValue.getText(),
		    nationalite,
		    numeroSS,
		    email,
		    "-1",
		    codePublic,
		    codePrive,
		    adresse,
		    telephone,
		    photoBytes,          
		    carteIdentiteBytes   
		);
		GestionArbreGenealogiqueBDD.ajoutRacineArbre(codePrive,codePublic,prenom); 
		String dateText = dateNaissanceValue.getText(); // "2025-05-12"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate = sdf.parse(dateText);
		Personne racine = new Personne(nom,prenom,utilDate,null,Genre.HOMME,codePrive,0);
		 
		GestionPersonneBDD.ajouterPersonneRacine(racine, codePrive);
    }


    /**
     * Handles the rejection of a registration request.
     * Sends a rejection email to the user.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void boutonRefuserAdhesion(ActionEvent event) {


        String expediteur = "administrateur@gmail.com";  // Ton adresse email
        String destinataire = emailBDD.getText();  // L'adresse de test

        // Configuration SMTP pour FakeSMTP avec le nouveau port
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");  // FakeSMTP sur localhost
        props.put("mail.smtp.port", "2525");  // Le nouveau port (2525 dans cet exemple)
        props.put("mail.smtp.auth", "false");  // Pas d'authentification

        // Création de la session SMTP
        Session session = Session.getInstance(props);

        try {
            // Création du message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur));  // L'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));  // Le destinataire
            message.setSubject("Information demande d'adhesion");  // Sujet de l'email
            message.setText("Bonjour,"
                    + "Vous demande a ete refuse."
                    + "Cordialement");  // Corps du message

            // Envoi du message
            Transport.send(message);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Information");
            alert.setHeaderText("La demande d'adhésion a été refusée");
            alert.setContentText("L'utilisateur sera notifé du refus.");
            alert.showAndWait();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
	
    /**
     * Handles the return button click event.
     * Returns to the administrator page.
     * 
     * @param event The action event that triggered this method
     */
    @FXML
    public void BoutonRetourPageAdmin(ActionEvent event) {
        main.afficherPageAdministrateur();
    }
}
