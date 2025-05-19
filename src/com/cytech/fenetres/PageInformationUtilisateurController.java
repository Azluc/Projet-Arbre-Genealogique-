package com.cytech.fenetres;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.cytech.Main;
import com.cytech.gestionBDD.GestionDemandeAdhesionBdd;
import com.cytech.gestionBDD.GestionUtilisateurBDD;

import javafx.embed.swing.SwingFXUtils;
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
import java.time.ZoneId;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;


public class PageInformationUtilisateurController {
    @SuppressWarnings("unused")
    private Main main;
    @FXML
	private Button BoutonRetourPageAdmin;
    @FXML
    private Label nomBDD;
    @FXML
    private Label prenomBDD;
    @FXML
    private Label dateNaissanceValue;
    @FXML
    private Label nationaliteBDD;
    @FXML
    private Label NumeroSecuBDD;
    @FXML
    private Label emailBDD;
    @FXML
    private Label adresseBDD;
    @FXML
    private Label numeroTelephoneBDD;
    @FXML
    private ImageView photoNumerique;
    @FXML
    private ImageView photoIdentite;
    @FXML
    private Button boutonAccepter;
    @FXML
    private Button boutonRefuser;

    private static final String utilisateur = "user";
    private static final String motDePasse = "Password123!";
    private static final String url = "jdbc:mysql://localhost:3306/arbre_genealogique";
    public void initialize() {

    }
    // Méthode pour afficher les informations utilisateur selon l'email
    public void afficherInformationsUtilisateur(String email) {
        initialize();
        System.out.println(email);
        String sql = "SELECT nom, prenom, date_naissance, nationalite, numero_securite_sociale, email, adresse, telephone, photo, carte_identite " +
                "FROM demande_adhesion WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);  // Remplace par l'email de l'utilisateur
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("nom"));
                // Récupérer les valeurs des champs
                nomBDD.setText("ab");
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

    // Méthode pour afficher l'image dans le ImageView
    private static void afficherImage(byte[] imageData, ImageView imageView) {
        if (imageData != null && imageData.length > 0) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            imageView.setImage(image);
        }
    }

    public void setMain(Main main) {
        this.main = main;

    }

    public static int genererNombreAleatoire() {
        Random random = new Random();

        return random.nextInt(1_000_000) + 1;
    }
    
    
     
    
    // Event Listener on Button[#boutonAccepter].onAction
    @FXML
    public void boutonAccepterAdhesion(ActionEvent event) throws IOException, SQLException {

        //preparation des codes publics et prives
        int codePublic = genererNombreAleatoire();
        int codePrive = genererNombreAleatoire();

        String codePublicToString = Integer.toString(codePublic);
        String codePriveToString = Integer.toString(codePrive);
        
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
            message.setSubject("Envoie des codes public et privées");  // Sujet de l'email
            message.setText("Bonjour,"
                    + "Vous trouverez ci-joint votre code public et votre code privée"
                    + "CodePublic : " +codePublicToString
                    + "CodePrivé : " +codePriveToString
                    +"Cordialement.");  // Corps du message

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
		
		List<String> listeLiens = GestionDemandeAdhesionBdd.getLiensParEmail(email);
		String lienPhoto = listeLiens.get(0);
		String lienIdentite = listeLiens.get(1);
		
		
        // Lire le fichier image et le convertir en tableau de bytes
        File fichierPhoto = new File(lienPhoto);
        byte[] photoBytes = Files.readAllBytes(fichierPhoto.toPath());
        
        File fichierCarteIdentite = new File(lienIdentite);
        byte[] carteIdentiteBytes = Files.readAllBytes(fichierCarteIdentite.toPath());
		
		
		/*
		Image imageIdentite = photoIdentite.getImage();
		BufferedImage bufferedImageIdentite = SwingFXUtils.fromFXImage(imageIdentite, null);
		ByteArrayOutputStream pieceIdentiteStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImageIdentite, "png", pieceIdentiteStream);
		byte[] pieceIdentiteBytes = pieceIdentiteStream.toByteArray();

		// Conversion de la photo numérique
		Image imageNumerique = photoNumerique.getImage();
		BufferedImage bufferedImageNumerique = SwingFXUtils.fromFXImage(imageNumerique, null);
		ByteArrayOutputStream photoNumeriqueStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImageNumerique, "png", photoNumeriqueStream);
		byte[] photoNumeriqueBytes = photoNumeriqueStream.toByteArray();
		*/
		// Appel de la méthode d'ajout
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
		    photoBytes,  // byte[] pour la pièce d'identité
		    carteIdentiteBytes  // byte[] pour la photo numérique
		);
    }


    // Event Listener on Button[#boutonRefuser].onAction
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
            message.setSubject("Envoie des codes public et privées");  // Sujet de l'email
            message.setText("Bonjour,"
                    + "Vous demande à été refusé."
                    + "Cordialement");  // Corps du message

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
    }
	
	
	 
	// Event Listener on Button[#BoutonRetourPageAdmin].onAction
		@FXML
		public void BoutonRetourPageAdmin(ActionEvent event) {
			main.afficherPageAdministrateur();
		}
}
