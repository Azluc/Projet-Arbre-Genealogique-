package com.cytech.classeTestsUnitaires;

import java.sql.*;
import java.util.Date;
import java.util.Calendar;

import com.cytech.gestionBDD.GestionPersonneBDD;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.Genre;

public class TestGestionPersonneBDD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/arbre_genealogique";
    private static final String UTILISATEUR = "user";
    private static final String MOT_DE_PASSE = "Password123!";
    
    private static int testsExecutes = 0;
    private static int testsReussis = 0;
    
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS POUR GestionPersonneBDD ===");
        
        // Preparation de la base de donnees pour les tests
        preparerBaseDeDonnees();
        
        // Execution des tests
        testAjouterPersonneRacineAvecGenre();
        testAjouterPersonneRacineAvecGenreNull();
        testAjouterPersonneRacineAvecDateDecesNull();
        testAjouterPersonneRacineAvecDateDecesRelle();
        testAjouterPersonneRacineAvecParametresComplets();
        
        testAjouterPersonneAvecGenre();
        testAjouterPersonneAvecGenreNull();
        testAjouterPersonneAvecDateDecesNull();
        testAjouterPersonneAvecDateDecesRelle();
        testAjouterPersonneAvecParametresComplets();
        
        testAjouterPersonneRacineEtPersonneComparaison();
        
        // Nettoyage de la base de donnees
        nettoyerBaseDeDonnees();
        
        // Resultats finaux
        System.out.println("\n=== RESULTATS DES TESTS ===");
        System.out.println("Tests executes: " + testsExecutes);
        System.out.println("Tests reussis: " + testsReussis);
        System.out.println("Tests echoues: " + (testsExecutes - testsReussis));
        
        if (testsReussis == testsExecutes) {
            System.out.println("TOUS LES TESTS ONT REUSSI !");
        } else {
            System.out.println("CERTAINS TESTS ONT ECHOUE !");
        }
    }
    
    private static void testAjouterPersonneRacineAvecGenre() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonneRacine avec genre masculin");
        
        try {
            Date dateNaissance = creerDate(1980, 5, 15);
            Personne personne = new Personne("Dupont", "Jean", dateNaissance, null, Genre.HOMME, 1, 0);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne, 1);
            
            if (verifierPersonneExiste("Dupont", "Jean", 1)) {
                System.out.println("REUSSI: Personne ajoutee avec succes");
                testsReussis++;
                
                // Verification du genre
                if (verifierGenrePersonne("Dupont", "Jean", "HOMME")) {
                    System.out.println("REUSSI: Genre correctement enregistre");
                } else {
                    System.out.println("ECHEC: Genre mal enregistre");
                }
            } else {
                System.out.println("ECHEC: Personne non trouvee dans la base");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneRacineAvecGenreNull() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonneRacine avec genre null");
        
        try {
            Date dateNaissance = creerDate(1975, 3, 20);
            Personne personne = new Personne("Martin", "Marie", dateNaissance, null, null, 1, 0);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne, 1);
            
            if (verifierPersonneExiste("Martin", "Marie", 1)) {
                System.out.println("REUSSI: Personne ajoutee avec genre null");
                testsReussis++;
                
                if (verifierGenrePersonne("Martin", "Marie", null)) {
                    System.out.println("REUSSI: Genre null correctement gere");
                } else {
                    System.out.println("ECHEC: Genre null mal gere");
                }
            } else {
                System.out.println("ECHEC: Personne avec genre null non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneRacineAvecDateDecesNull() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonneRacine avec date de deces null");
        
        try {
            Date dateNaissance = creerDate(1990, 8, 10);
            Personne personne = new Personne("Durand", "Pierre", dateNaissance, null, Genre.HOMME, 1, 0);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne, 1);
            
            if (verifierPersonneExiste("Durand", "Pierre", 1)) {
                System.out.println("REUSSI: Personne vivante ajoutee");
                testsReussis++;
                
                if (verifierDateDecesPersonne("Durand", "Pierre", null)) {
                    System.out.println("REUSSI: Date de deces null correctement geree");
                } else {
                    System.out.println("ECHEC: Date de deces null mal geree");
                }
            } else {
                System.out.println("ECHEC: Personne vivante non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneRacineAvecDateDecesRelle() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonneRacine avec date de deces reelle");
        
        try {
            Date dateNaissance = creerDate(1950, 12, 5);
            Date dateDeces = creerDate(2020, 6, 15);
            Personne personne = new Personne("Moreau", "Francoise", dateNaissance, dateDeces, Genre.FEMME, 1, 0);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne, 1);
            
            if (verifierPersonneExiste("Moreau", "Francoise", 1)) {
                System.out.println("REUSSI: Personne decedee ajoutee");
                testsReussis++;
                
                if (verifierDateDecesPersonne("Moreau", "Francoise", dateDeces)) {
                    System.out.println("REUSSI: Date de deces correctement enregistree");
                } else {
                    System.out.println("ECHEC: Date de deces mal enregistree");
                }
            } else {
                System.out.println("ECHEC: Personne decedee non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneRacineAvecParametresComplets() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonneRacine avec tous les parametres");
        
        try {
            Date dateNaissance = creerDate(1985, 4, 22);
            Date dateDeces = creerDate(2022, 11, 8);
            Personne personne = new Personne("Leroy", "Antoine", dateNaissance, dateDeces, Genre.HOMME, 2, 1);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne, 2);
            
            if (verifierPersonneExiste("Leroy", "Antoine", 2)) {
                System.out.println("REUSSI: Personne avec parametres complets ajoutee");
                testsReussis++;
                
                boolean tousParametresOk = verifierGenrePersonne("Leroy", "Antoine", "HOMME") &&
                                         verifierDateDecesPersonne("Leroy", "Antoine", dateDeces) &&
                                         verifierProfondeurPersonne("Leroy", "Antoine", 1);
                
                if (tousParametresOk) {
                    System.out.println("REUSSI: Tous les parametres correctement enregistres");
                } else {
                    System.out.println("ECHEC: Certains parametres mal enregistres");
                }
            } else {
                System.out.println("ECHEC: Personne avec parametres complets non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneAvecGenre() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonne avec genre feminin");
        
        try {
            Date dateNaissance = creerDate(1992, 7, 3);
            Personne personne = new Personne("Bernard", "Sophie", dateNaissance, null, Genre.FEMME, 1, 2);
            
            GestionPersonneBDD.ajouterPersonne(personne, 1);
            
            if (verifierPersonneExiste("Bernard", "Sophie", 1)) {
                System.out.println("REUSSI: Personne ajoutee avec ajouterPersonne");
                testsReussis++;
                
                if (verifierGenrePersonne("Bernard", "Sophie", "FEMME")) {
                    System.out.println("REUSSI: Genre feminin correctement enregistre");
                } else {
                    System.out.println("ECHEC: Genre feminin mal enregistre");
                }
            } else {
                System.out.println("ECHEC: Personne non ajoutee avec ajouterPersonne");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneAvecGenreNull() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonne avec genre null");
        
        try {
            Date dateNaissance = creerDate(1988, 1, 17);
            Personne personne = new Personne("Petit", "Alex", dateNaissance, null, null, 1, 1);
            
            GestionPersonneBDD.ajouterPersonne(personne, 1);
            
            if (verifierPersonneExiste("Petit", "Alex", 1)) {
                System.out.println("REUSSI: Personne avec genre null ajoutee");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Personne avec genre null non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneAvecDateDecesNull() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonne avec date de deces null");
        
        try {
            Date dateNaissance = creerDate(1995, 9, 25);
            Personne personne = new Personne("Roux", "Emma", dateNaissance, null, Genre.FEMME, 2, 0);
            
            GestionPersonneBDD.ajouterPersonne(personne, 2);
            
            if (verifierPersonneExiste("Roux", "Emma", 2)) {
                System.out.println("REUSSI: Personne vivante ajoutee avec ajouterPersonne");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Personne vivante non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneAvecDateDecesRelle() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonne avec date de deces reelle");
        
        try {
            Date dateNaissance = creerDate(1960, 2, 14);
            Date dateDeces = creerDate(2021, 8, 30);
            Personne personne = new Personne("Blanc", "Robert", dateNaissance, dateDeces, Genre.HOMME, 2, 1);
            
            GestionPersonneBDD.ajouterPersonne(personne, 2);
            
            if (verifierPersonneExiste("Blanc", "Robert", 2)) {
                System.out.println("REUSSI: Personne decedee ajoutee avec ajouterPersonne");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Personne decedee non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneAvecParametresComplets() {
        testsExecutes++;
        System.out.println("\nTest: ajouterPersonne avec tous les parametres");
        
        try {
            Date dateNaissance = creerDate(1970, 10, 12);
            Date dateDeces = creerDate(2019, 5, 7);
            Personne personne = new Personne("Girard", "Claude", dateNaissance, dateDeces, Genre.HOMME, 3, 2);
            
            GestionPersonneBDD.ajouterPersonne(personne, 3);
            
            if (verifierPersonneExiste("Girard", "Claude", 3)) {
                System.out.println("REUSSI: Personne complete ajoutee avec ajouterPersonne");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Personne complete non ajoutee");
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    private static void testAjouterPersonneRacineEtPersonneComparaison() {
        testsExecutes++;
        System.out.println("\nTest: Comparaison ajouterPersonneRacine vs ajouterPersonne");
        
        try {
            Date dateNaissance = creerDate(1983, 6, 9);
            Personne personne1 = new Personne("Comparaison", "Test1", dateNaissance, null, Genre.FEMME, 4, 0);
            Personne personne2 = new Personne("Comparaison", "Test2", dateNaissance, null, Genre.FEMME, 4, 0);
            
            GestionPersonneBDD.ajouterPersonneRacine(personne1, 4);
            GestionPersonneBDD.ajouterPersonne(personne2, 4);
            
            boolean personne1Existe = verifierPersonneExiste("Comparaison", "Test1", 4);
            boolean personne2Existe = verifierPersonneExiste("Comparaison", "Test2", 4);
            
            if (personne1Existe && personne2Existe) {
                System.out.println("REUSSI: Les deux methodes fonctionnent identiquement");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Difference de comportement entre les deux methodes");
                System.out.println("ajouterPersonneRacine: " + (personne1Existe ? "OK" : "ECHEC"));
                System.out.println("ajouterPersonne: " + (personne2Existe ? "OK" : "ECHEC"));
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors du test - " + e.getMessage());
        }
    }
    
    // Methodes utilitaires
    
    private static Date creerDate(int annee, int mois, int jour) {
        Calendar cal = Calendar.getInstance();
        cal.set(annee, mois - 1, jour); // mois - 1 car Calendar utilise 0-11
        return cal.getTime();
    }
    
    private static void preparerBaseDeDonnees() {
        System.out.println("Preparation de la base de donnees pour les tests...");
        // Ici on pourrait creer des tables de test si necessaire
        // Pour ce test, on assume que la table Personne existe deja
    }
    
    private static void nettoyerBaseDeDonnees() {
        System.out.println("Nettoyage de la base de donnees...");
        try {
            Connection conn = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            String sql = "DELETE FROM Personne WHERE nom IN ('Dupont', 'Martin', 'Durand', 'Moreau', 'Leroy', 'Bernard', 'Petit', 'Roux', 'Blanc', 'Girard', 'Comparaison')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int nbSupprimes = stmt.executeUpdate();
            System.out.println(nbSupprimes + " enregistrements de test supprimes");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors du nettoyage: " + e.getMessage());
        }
    }
    
    private static boolean verifierPersonneExiste(String nom, String prenom, int idArbre) {
        try {
            Connection conn = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            String sql = "SELECT COUNT(*) FROM Personne WHERE nom = ? AND prenom = ? AND id_arbre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setInt(3, idArbre);
            
            ResultSet rs = stmt.executeQuery();
            boolean existe = false;
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return existe;
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la verification: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean verifierGenrePersonne(String nom, String prenom, String genreAttendu) {
        try {
            Connection conn = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            String sql = "SELECT genre FROM Personne WHERE nom = ? AND prenom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            
            ResultSet rs = stmt.executeQuery();
            boolean correct = false;
            if (rs.next()) {
                String genreBDD = rs.getString("genre");
                if (genreAttendu == null) {
                    correct = (genreBDD == null);
                } else {
                    correct = genreAttendu.equals(genreBDD);
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return correct;
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la verification du genre: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean verifierDateDecesPersonne(String nom, String prenom, Date dateDecesAttendue) {
        try {
            Connection conn = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            String sql = "SELECT dateDeces FROM Personne WHERE nom = ? AND prenom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            
            ResultSet rs = stmt.executeQuery();
            boolean correct = false;
            if (rs.next()) {
                Date dateDecesBDD = rs.getDate("dateDeces");
                if (dateDecesAttendue == null) {
                    correct = (dateDecesBDD == null);
                } else {
                    correct = dateDecesAttendue.equals(dateDecesBDD);
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return correct;
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la verification de la date de deces: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean verifierProfondeurPersonne(String nom, String prenom, int profondeurAttendue) {
        try {
            Connection conn = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            String sql = "SELECT profondeur FROM Personne WHERE nom = ? AND prenom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            
            ResultSet rs = stmt.executeQuery();
            boolean correct = false;
            if (rs.next()) {
                int profondeurBDD = rs.getInt("profondeur");
                correct = (profondeurAttendue == profondeurBDD);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return correct;
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la verification de la profondeur: " + e.getMessage());
            return false;
        }
    }
}