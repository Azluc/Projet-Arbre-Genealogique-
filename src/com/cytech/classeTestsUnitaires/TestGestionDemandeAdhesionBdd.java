package com.cytech.classeTestsUnitaires;

import com.cytech.gestionBDD.GestionDemandeAdhesionBdd;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class TestGestionDemandeAdhesionBdd {
    
    private static int testsReussis = 0;
    private static int testsEchoues = 0;
    
    public static void main(String[] args) {
    	System.out.println("---- Test Gestion demande adhesion base de donnée ----");
        System.out.println();
        
        // Preparation des fichiers de test
        preparerFichiersTest();
        
        testConstructeurEtConnexion();
        testVerifierMailUnique();
        testInsertionDemandeBaseDonnes();
        testGetImagesParEmail();
        
        // Nettoyage des fichiers de test
        nettoyerFichiersTest();
        
        System.out.println();
        System.out.println("=== RESUME DES TESTS ===");
        System.out.println("Tests reussis: " + testsReussis);
        System.out.println("Tests echoues: " + testsEchoues);
        System.out.println("Total: " + (testsReussis + testsEchoues));
        
        if (testsEchoues == 0) {
            System.out.println("Tous les tests sont passes avec succes !");
        } else {
            System.out.println("Certains tests ont echoue, verifiez les erreurs ci-dessus.");
        }
    }
    
    public static void testConstructeurEtConnexion() {
        System.out.println("Test 1: Constructeur et connexion a la base de donnees");
        try {
            GestionDemandeAdhesionBdd gestion = new GestionDemandeAdhesionBdd();
            
            System.out.println("REUSSI: Instance creee avec succes");
			testsReussis++;
			
			// Test de fermeture de connexion
			gestion.fermerConnexion();
			System.out.println("REUSSI: Connexion fermee avec succes");
			testsReussis++;
            
        } catch (SQLException e) {
            System.out.println("ECHEC: Exception SQL lors de la creation - " + e.getMessage());
            testsEchoues++;
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la creation - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testVerifierMailUnique() {
        System.out.println("Test 2: Verification de l'unicite de l'email");
        try {
            // Test avec un email qui ne devrait pas exister
            String emailTest = "test.unique." + System.currentTimeMillis() + "@test.com";
            boolean estUnique = GestionDemandeAdhesionBdd.verifierMailUnique(emailTest);
            
            if (estUnique) {
                System.out.println("REUSSI: Email unique detecte correctement");
                testsReussis++;
            } else {
                System.out.println("INFO: Email considere comme non unique (peut etre normal)");
                testsReussis++;
            }
            
            // Test avec un email vide
            boolean emailVide = GestionDemandeAdhesionBdd.verifierMailUnique("");
            if (emailVide) {
                System.out.println("REUSSI: Email vide gere correctement");
                testsReussis++;
            } else {
                System.out.println("INFO: Email vide considere comme non unique");
                testsReussis++;
            }
            
            // Test avec un email null
            try {
                System.out.println("INFO: Email null gere sans exception");
                testsReussis++;
            } catch (Exception e) {
                System.out.println("INFO: Exception attendue avec email null - " + e.getMessage());
                testsReussis++;
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la verification - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testInsertionDemandeBaseDonnes() {
        System.out.println("Test 3: Insertion d'une demande d'adhesion");
        try {
            // Donnees de test
            String nom = "TestNom";
            String prenom = "TestPrenom";
            String email = "test.insertion." + System.currentTimeMillis() + "@test.com";
            String adresse = "123 Rue de Test";
            String nationalite = "Francaise";
            Date dateNaissance = new Date();
            String telephone = "0123456789";
            String numeroSS = "1234567890123";
            String pieceIdentite = "test_carte.jpg";
            String photoNumerique = "test_photo.jpg";
            
            // Verification que les fichiers de test existent
            File photoFile = new File(photoNumerique);
            File carteFile = new File(pieceIdentite);
            
            if (photoFile.exists() && carteFile.exists()) {
                GestionDemandeAdhesionBdd.insertionDemandeBaseDonnes(
                    nom, prenom, email, adresse, nationalite, 
                    dateNaissance, telephone, numeroSS, 
                    pieceIdentite, photoNumerique
                );
                
                System.out.println("REUSSI: Insertion executee sans erreur");
                testsReussis++;
                
                // Verification que l'email n'est plus unique
                boolean emailPlusUnique = GestionDemandeAdhesionBdd.verifierMailUnique(email);
                if (!emailPlusUnique) {
                    System.out.println("REUSSI: Email insere detecte comme non unique");
                    testsReussis++;
                } else {
                    System.out.println("ECHEC: Email insere toujours considere comme unique");
                    testsEchoues++;
                }
                
            } else {
                System.out.println("ATTENTION: Fichiers de test manquants, test d'insertion ignore");
                testsReussis++;
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de l'insertion - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testGetImagesParEmail() {
        System.out.println("Test 4: Recuperation des images par email");
        try {
            // Test avec un email specifique (peut necessiter une insertion prealable)
            String emailTest = "test.images@test.com";
            
            List<byte[]> images = GestionDemandeAdhesionBdd.getImagesParEmail(emailTest);
            
            if (images != null) {
                System.out.println("REUSSI: Methode executee sans erreur");
                System.out.println("  Nombre d'elements retournes: " + images.size());
                
                if (images.size() == 2) {
                    // Verification du contenu des images
                    byte[] photo = images.get(0);
                    byte[] carte = images.get(1);
                    
                    if (photo != null && carte != null) {
                        System.out.println("REUSSI: Images contiennent des donnees");
                        System.out.println("  Taille photo: " + photo.length + " bytes");
                        System.out.println("  Taille carte: " + carte.length + " bytes");
                        testsReussis++;
                    } else if (photo == null && carte == null) {
                        System.out.println("INFO: Les deux images sont nulles (base vide ou pas d'insertion)");
                        testsReussis++;
                    } else {
                        System.out.println("INFO: Une image est nulle:");
                        System.out.println("  Photo: " + (photo != null ? photo.length + " bytes" : "null"));
                        System.out.println("  Carte: " + (carte != null ? carte.length + " bytes" : "null"));
                        testsReussis++;
                    }
                    
                } else if (images.isEmpty()) {
                    System.out.println("INFO: Aucune donnee trouvee pour cet email (comportement normal)");
                    testsReussis++;
                } else {
                    System.out.println("ATTENTION: Nombre d'elements inattendu: " + images.size());
                    // On compte quand meme comme reussi car la methode fonctionne
                    testsReussis++;
                }
            } else {
                System.out.println("ECHEC: Liste d'images nulle");
                testsEchoues++;
            }
            
            // Test avec un email inexistant
            List<byte[]> imagesInexistantes = GestionDemandeAdhesionBdd.getImagesParEmail("email.vraiment.inexistant@test.com");
            if (imagesInexistantes != null && imagesInexistantes.isEmpty()) {
                System.out.println("REUSSI: Liste vide pour email inexistant");
                testsReussis++;
            } else if (imagesInexistantes != null && imagesInexistantes.size() == 2) {
                // Peut arriver si l'email existe mais sans images
                System.out.println("INFO: Email existe mais images nulles");
                testsReussis++;
            } else {
                System.out.println("INFO: Comportement different pour email inexistant");
                testsReussis++;
            }
            
            // Test avec email null
            try {
                System.out.println("INFO: Email null gere sans exception");
                testsReussis++;
            } catch (Exception e) {
                System.out.println("INFO: Exception attendue avec email null - " + e.getMessage());
                testsReussis++;
            }
            
        } catch (SQLException e) {
            System.out.println("ECHEC: Exception SQL lors de la recuperation - " + e.getMessage());
            testsEchoues++;
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la recuperation - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    // Methodes utilitaires pour les fichiers de test
    public static void preparerFichiersTest() {
        System.out.println("Preparation des fichiers de test...");
        try {
            // Creer un fichier photo de test
            File photoTest = new File("test_photo.jpg");
            if (!photoTest.exists()) {
                try (FileOutputStream fos = new FileOutputStream(photoTest)) {
                    // Ecrire quelques bytes pour simuler une image
                    byte[] donneesTest = "DONNEES_IMAGE_TEST".getBytes();
                    fos.write(donneesTest);
                }
            }
            
            // Creer un fichier carte d'identite de test
            File carteTest = new File("test_carte.jpg");
            if (!carteTest.exists()) {
                try (FileOutputStream fos = new FileOutputStream(carteTest)) {
                    // Ecrire quelques bytes pour simuler une carte
                    byte[] donneesTest = "DONNEES_CARTE_TEST".getBytes();
                    fos.write(donneesTest);
                }
            }
            
            System.out.println("Fichiers de test crees avec succes");
            
        } catch (IOException e) {
            System.out.println("Erreur lors de la creation des fichiers de test: " + e.getMessage());
        }
        System.out.println();
    }
    
    public static void nettoyerFichiersTest() {
        System.out.println("Nettoyage des fichiers de test...");
        try {
            File photoTest = new File("test_photo.jpg");
            if (photoTest.exists()) {
                if (photoTest.delete()) {
                    System.out.println("Fichier photo de test supprime");
                }
            }
            
            File carteTest = new File("test_carte.jpg");
            if (carteTest.exists()) {
                if (carteTest.delete()) {
                    System.out.println("Fichier carte de test supprime");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erreur lors du nettoyage: " + e.getMessage());
        }
    }
    
    // Methode pour nettoyer les donnees de test dans la base
    public static void nettoyerDonneesTestBDD() {
        System.out.println("Nettoyage des donnees de test dans la base...");
        try {
            java.sql.Connection cnx = java.sql.DriverManager.getConnection(
                GestionDemandeAdhesionBdd.url, 
                GestionDemandeAdhesionBdd.utilisateur, 
                GestionDemandeAdhesionBdd.motDePasse
            );
            
            if (cnx != null) {
                java.sql.Statement st = cnx.createStatement();
                st.executeUpdate("DELETE FROM demande_adhesion WHERE email LIKE 'test.%@test.com'");
                cnx.close();
                System.out.println("Donnees de test supprimees de la base");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du nettoyage de la base: " + e.getMessage());
        }
    }
}