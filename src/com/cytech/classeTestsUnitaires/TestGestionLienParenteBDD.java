package com.cytech.classeTestsUnitaires;

import java.util.Set;
import com.cytech.gestionBDD.GestionLienParenteBDD;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;
import com.cytech.classeProjet.Genre;

public class TestGestionLienParenteBDD {
    
    private static int testsReussis = 0;
    private static int testsEchoues = 0;
    private static int totalTests = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Tests de la classe GestionLienParenteBDD ===");
        System.out.println();
        
        // Exécution des tests
        testRecupererPersonnesDecedees();
        testRecupererPersonnesVivantes();
        testRecupererFemmes();
        testRecupererHommes();
        testEnregistrerLienDansBDD();
        testAvecIdArbreInexistant();
        testMethodesPersonne();
        
        // Résumé des tests
        afficherResultats();
    }
    
    /**
     * Test de la méthode recupererPersonnesDecedees
     */
    public static void testRecupererPersonnesDecedees() {
        System.out.println("Test recupererPersonnesDecedees:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> decedes = GestionLienParenteBDD.recupererPersonnesDecedees(idArbreTest);
            
            if (decedes != null) {
                System.out.println("  - La methode retourne un ensemble non null: PASSE");
                testsReussis++;
                
                System.out.println("  - Nombre de personnes decedees trouvees: " + decedes.size());
                if (!decedes.isEmpty()) {
                    System.out.println("  - Exemple de personnes decedees: " + decedes.toString());
                }
            } else {
                System.out.println("  - La methode retourne null: ECHEC");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test de la méthode recupererPersonnesVivantes
     */
    public static void testRecupererPersonnesVivantes() {
        System.out.println("Test recupererPersonnesVivantes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> vivants = GestionLienParenteBDD.recupererPersonnesVivantes(idArbreTest);
            
            if (vivants != null) {
                System.out.println("  - La methode retourne un ensemble non null: PASSE");
                testsReussis++;
                
                System.out.println("  - Nombre de personnes vivantes trouvees: " + vivants.size());
                if (!vivants.isEmpty()) {
                    System.out.println("  - Exemple de personnes vivantes: " + vivants.toString());
                }
            } else {
                System.out.println("  - La methode retourne null: ECHEC");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test de la méthode recupererFemmes
     */
    public static void testRecupererFemmes() {
        System.out.println("Test recupererFemmes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> femmes = GestionLienParenteBDD.recupererFemmes(idArbreTest);
            
            if (femmes != null) {
                System.out.println("  - La methode retourne un ensemble non null: PASSE");
                testsReussis++;
                
                System.out.println("  - Nombre de femmes trouvees: " + femmes.size());
                if (!femmes.isEmpty()) {
                    System.out.println("  - Exemple de femmes: " + femmes.toString());
                }
            } else {
                System.out.println("  - La methode retourne null: ECHEC");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test de la méthode recupererHommes
     */
    public static void testRecupererHommes() {
        System.out.println("Test recupererHommes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> hommes = GestionLienParenteBDD.recupererHommes(idArbreTest);
            
            if (hommes != null) {
                System.out.println("  - La methode retourne un ensemble non null: PASSE");
                testsReussis++;
                
                System.out.println("  - Nombre d'hommes trouves: " + hommes.size());
                if (!hommes.isEmpty()) {
                    System.out.println("  - Exemple d'hommes: " + hommes.toString());
                }
            } else {
                System.out.println("  - La methode retourne null: ECHEC");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test de la méthode enregistrerLienDansBDD
     */
    public static void testEnregistrerLienDansBDD() {
        System.out.println("Test enregistrerLienDansBDD:");
        totalTests++;
        
        try {
            // Création de personnes de test avec dates à null
            Personne parent = new Personne("Dupont", "Jean", "Francaise", null, null);
            parent.setId_arbre(1);
            parent.setGenre(Genre.HOMME);
            
            Personne enfant = new Personne("Dupont", "Marie", "Francaise", null, null);
            enfant.setId_arbre(1);
            enfant.setGenre(Genre.FEMME);
            
            // Test relation PARENT_ENFANT
            GestionLienParenteBDD.enregistrerLienDansBDD(parent, enfant, TypeRelation.PARENT_ENFANT, "Pere");
            System.out.println("  - Enregistrement relation PARENT_ENFANT: PASSE");
            
            // Test relation FRERE_SOEUR
            Personne frere = new Personne("Dupont", "Paul", "Francaise", null, null);
            frere.setId_arbre(1);
            frere.setGenre(Genre.HOMME);
            
            GestionLienParenteBDD.enregistrerLienDansBDD(enfant, frere, TypeRelation.FRERE_SOEUR, "Soeur");
            System.out.println("  - Enregistrement relation FRERE_SOEUR: PASSE");
            
            testsReussis++;
            
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test avec un ID d'arbre inexistant
     */
    public static void testAvecIdArbreInexistant() {
        System.out.println("Test avec ID arbre inexistant:");
        totalTests++;
        
        try {
            int idArbreInexistant = 99999;
            Set<String> resultats = GestionLienParenteBDD.recupererPersonnesVivantes(idArbreInexistant);
            
            if (resultats != null && resultats.isEmpty()) {
                System.out.println("  - Retourne un ensemble vide pour ID inexistant: PASSE");
                testsReussis++;
            } else if (resultats == null) {
                System.out.println("  - Retourne null pour ID inexistant: ECHEC");
                testsEchoues++;
            } else {
                System.out.println("  - Retourne des donnees inattendues: ECHEC");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Test des méthodes de la classe Personne utilisées dans les tests
     */
    public static void testMethodesPersonne() {
        System.out.println("Test des methodes de la classe Personne:");
        totalTests++;
        
        try {
            Personne personne = new Personne("Martin", "Pierre", "Francaise", null, null);
            personne.setId_arbre(1);
            personne.setGenre(Genre.HOMME);
            
            // Test des getters/setters
            if ("Martin".equals(personne.getNom()) && "Pierre".equals(personne.getPrenom())) {
                System.out.println("  - Getters nom et prenom: PASSE");
                
                if (personne.getId_arbre() == 1) {
                    System.out.println("  - Getter/Setter id_arbre: PASSE");
                    
                    if (personne.getGenre() == Genre.HOMME) {
                        System.out.println("  - Getter/Setter genre: PASSE");
                        testsReussis++;
                    } else {
                        System.out.println("  - Getter/Setter genre: ECHEC");
                        testsEchoues++;
                    }
                } else {
                    System.out.println("  - Getter/Setter id_arbre: ECHEC");
                    testsEchoues++;
                }
            } else {
                System.out.println("  - Getters nom et prenom: ECHEC");
                testsEchoues++;
            }
            
        } catch (Exception e) {
            System.out.println("  - Exception lors du test: " + e.getMessage() + " - ECHEC");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Affichage du résumé des tests
     */
    public static void afficherResultats() {
        System.out.println("=== RESUME DES TESTS ===");
        System.out.println("Total des tests executes: " + totalTests);
        System.out.println("Tests reussis: " + testsReussis);
        System.out.println("Tests echoues: " + testsEchoues);
        
        if (testsEchoues == 0) {
            System.out.println("Tous les tests sont passes avec succes!");
        } else {
            System.out.println("Il y a " + testsEchoues + " test(s) qui ont echoue.");
        }
        
        
    }
}