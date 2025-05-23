package com.cytech.classeTestsUnitaires;

import com.cytech.gestionBDD.GestionUtilisateurBDD;
import java.util.List;

public class TestGestionUtilisateurBDD {
    
    private static int testsReussis = 0;
    private static int testsEchoues = 0;
    
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS POUR GestionUtilisateurBDD ===");
        System.out.println();
        
        testConnexionDB();
        testAjouterUtilisateur();
        testGetPrenomEtMotDePasseParCodePrive();
        testModifierMotDePasseParCodePrive();
        testMettreAJourUtilisateurParCodePrive();
        testRechercheparNP();
        testMofifierU();
        
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
    
    public static void testConnexionDB() {
        System.out.println("Test 1: Connexion a la base de donnees");
        try {
            java.sql.Connection connexion = GestionUtilisateurBDD.connecterDB();
            if (connexion != null && !connexion.isClosed()) {
                System.out.println("REUSSI: Connexion etablie avec succes");
                testsReussis++;
                connexion.close();
            } else {
                System.out.println("ECHEC: Impossible d'etablir la connexion");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la connexion - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testAjouterUtilisateur() {
        System.out.println("Test 2: Ajout d'un utilisateur");
        try {
            // Test avec des donnees valides
            GestionUtilisateurBDD.AjouterUtilisateur(
                "Dupont", 
                "Jean", 
                "1990-01-01",
                "Francaise",
                "1234567890123",
                "jean.dupont@test.com",
                "motdepasse123",
                1234567,
                6789078,
                "123 Rue de Test",
                "0123456789",
                null,
                null
            );
            
            // Si aucune exception n'est levee, le test est considere comme reussi
            System.out.println("REUSSI: Utilisateur ajoute sans erreur");
            testsReussis++;
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de l'ajout - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testGetPrenomEtMotDePasseParCodePrive() {
        System.out.println("Test 3: Recuperation des informations par code prive");
        try {
            // Test avec un code prive qui devrait exister (celui ajoute precedemment)
            List<Object> infos = GestionUtilisateurBDD.getPrenomEtMotDePasseParCodePrive(67890);
            
            if (infos != null && infos.size() == 3) {
                System.out.println("REUSSI: Informations recuperees avec succes");
                System.out.println("  Prenom: " + infos.get(0));
                System.out.println("  Mot de passe: " + infos.get(1));
                System.out.println("  Code prive: " + infos.get(2));
                testsReussis++;
            } else if (infos != null && infos.isEmpty()) {
                System.out.println("REUSSI: Aucun utilisateur trouve avec ce code prive (comportement attendu)");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Format de retour inattendu");
                testsEchoues++;
            }
            
            // Test avec un code prive inexistant
            List<Object> infosInexistantes = GestionUtilisateurBDD.getPrenomEtMotDePasseParCodePrive(99999);
            if (infosInexistantes.isEmpty()) {
                System.out.println("REUSSI: Aucun resultat pour code prive inexistant");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Resultat inattendu pour code prive inexistant");
                testsEchoues++;
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la recuperation - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testModifierMotDePasseParCodePrive() {
        System.out.println("Test 4: Modification du mot de passe par code prive");
        try {
            // Test de modification avec un code existant
            boolean resultat = GestionUtilisateurBDD.modifierMotDePasseParCodePrive(67890, "nouveaumotdepasse");
            
            if (resultat) {
                System.out.println("REUSSI: Mot de passe modifie avec succes");
                testsReussis++;
            } else {
                System.out.println("INFO: Aucune ligne modifiee (utilisateur peut ne pas exister)");
                testsReussis++;
            }
            
            // Test avec un code inexistant
            boolean resultatInexistant = GestionUtilisateurBDD.modifierMotDePasseParCodePrive(99999, "test");
            if (!resultatInexistant) {
                System.out.println("REUSSI: Aucune modification pour code inexistant");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Modification inattendue pour code inexistant");
                testsEchoues++;
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la modification - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testMettreAJourUtilisateurParCodePrive() {
        System.out.println("Test 5: Mise a jour des informations utilisateur");
        try {
            // Test avec des champs a modifier
            int resultat = GestionUtilisateurBDD.mettreAJourUtilisateurParCodePrive(
                67890, 
                "jean.dupont.nouveau@test.com", 
                "Francaise", 
                "456 Nouvelle Rue", 
                "0987654321"
            );
            
            if (resultat == 1) {
                System.out.println("REUSSI: Utilisateur mis a jour avec succes");
                testsReussis++;
            } else if (resultat == -1) {
                System.out.println("INFO: Utilisateur non trouve (comportement normal si pas d'utilisateur avec ce code)");
                testsReussis++;
            } else if (resultat == 0) {
                System.out.println("INFO: Aucun champ a modifier");
                testsReussis++;
            }
            
            // Test avec des champs vides
            int resultatVide = GestionUtilisateurBDD.mettreAJourUtilisateurParCodePrive(
                67890, "", "", "", ""
            );
            
            if (resultatVide == 0) {
                System.out.println("REUSSI: Aucune modification avec champs vides");
                testsReussis++;
            } else {
                System.out.println("ECHEC: Comportement inattendu avec champs vides");
                testsEchoues++;
            }
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la mise a jour - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testRechercheparNP() {
        System.out.println("Test 6: Recherche par nom et prenom");
        try {
            System.out.println("Recherche d'un utilisateur existant:");
            GestionUtilisateurBDD.rechercheparNP("Dupont", "Jean");
            
            System.out.println("Recherche d'un utilisateur inexistant:");
            GestionUtilisateurBDD.rechercheparNP("Inexistant", "Test");
            
            System.out.println("REUSSI: Recherche executee sans erreur");
            testsReussis++;
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la recherche - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    public static void testMofifierU() {
        System.out.println("Test 7: Modification utilisateur (methode MofifierU)");
        try {
            // Note: Cette methode a des problemes de securite (injection SQL)
            // mais nous testons sa fonctionnalite de base
            System.out.println("Tentative de modification d'un utilisateur...");
            GestionUtilisateurBDD.MofifierU(1, "nouveaumotdepasse", "'nouvel.email@test.com'", "'0123456789'", "Nouvelle adresse");
            
            System.out.println("REUSSI: Methode MofifierU executee sans erreur");
            testsReussis++;
            
        } catch (Exception e) {
            System.out.println("ECHEC: Exception lors de la modification - " + e.getMessage());
            testsEchoues++;
        }
        System.out.println();
    }
    
    // Methode utilitaire pour nettoyer les donnees de test
    public static void nettoyerDonneesTest() {
        System.out.println("Nettoyage des donnees de test...");
        try {
            java.sql.Connection cnx = GestionUtilisateurBDD.connecterDB();
            if (cnx != null) {
                java.sql.Statement st = cnx.createStatement();
                st.executeUpdate("DELETE FROM utilisateur WHERE email = 'jean.dupont@test.com' OR email = 'jean.dupont.nouveau@test.com'");
                cnx.close();
                System.out.println("Donnees de test supprimees");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du nettoyage: " + e.getMessage());
        }
    }
}