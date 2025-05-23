package com.cytech.classeTestsUnitaires;

import java.util.Set;
import com.cytech.gestionBDD.GestionLienParenteBDD;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;
import com.cytech.classeProjet.Genre;

/**
 * Test class for GestionLienParenteBDD functionality.
 * This class contains unit tests for various methods in the GestionLienParenteBDD class,
 * including tests for retrieving deceased and living persons, men and women,
 * and testing relationship registration in the database.
 */
public class TestGestionLienParenteBDD {
    
    private static int testsReussis = 0;
    private static int testsEchoues = 0;
    private static int totalTests = 0;
    
    /**
     * Main method to run all tests for GestionLienParenteBDD.
     * Executes all test methods and displays a summary of results.
     */
    public static void main(String[] args) {
        System.out.println("=== Tests for GestionLienParenteBDD class ===");
        System.out.println();
        
        // Execute tests
        testRecupererPersonnesDecedees();
        testRecupererPersonnesVivantes();
        testRecupererFemmes();
        testRecupererHommes();
        testEnregistrerLienDansBDD();
        testAvecIdArbreInexistant();
        testMethodesPersonne();
        
        // Test summary
        afficherResultats();
    }
    
    /**
     * Tests the recupererPersonnesDecedees method.
     * Verifies that the method correctly retrieves deceased persons from the database.
     */
    public static void testRecupererPersonnesDecedees() {
        System.out.println("Test recupererPersonnesDecedees:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> decedes = GestionLienParenteBDD.recupererPersonnesDecedees(idArbreTest);
            
            if (decedes != null) {
                System.out.println("  - Method returns a non-null set: PASS");
                testsReussis++;
                
                System.out.println("  - Number of deceased people found: " + decedes.size());
                if (!decedes.isEmpty()) {
                    System.out.println("  - Example of deceased people: " + decedes.toString());
                }
            } else {
                System.out.println("  - Method returns null: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests the recupererPersonnesVivantes method.
     * Verifies that the method correctly retrieves living persons from the database.
     */
    public static void testRecupererPersonnesVivantes() {
        System.out.println("Test recupererPersonnesVivantes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> vivants = GestionLienParenteBDD.recupererPersonnesVivantes(idArbreTest);
            
            if (vivants != null) {
                System.out.println("  - Method returns a non-null set: PASS");
                testsReussis++;
                
                System.out.println("  - Number of living people found: " + vivants.size());
                if (!vivants.isEmpty()) {
                    System.out.println("  - Example of living people: " + vivants.toString());
                }
            } else {
                System.out.println("  - Method returns null: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests the recupererFemmes method.
     * Verifies that the method correctly retrieves women from the database.
     */
    public static void testRecupererFemmes() {
        System.out.println("Test recupererFemmes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> femmes = GestionLienParenteBDD.recupererFemmes(idArbreTest);
            
            if (femmes != null) {
                System.out.println("  - Method returns a non-null set: PASS");
                testsReussis++;
                
                System.out.println("  - Number of women found: " + femmes.size());
                if (!femmes.isEmpty()) {
                    System.out.println("  - Example of women: " + femmes.toString());
                }
            } else {
                System.out.println("  - Method returns null: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests the recupererHommes method.
     * Verifies that the method correctly retrieves men from the database.
     */
    public static void testRecupererHommes() {
        System.out.println("Test recupererHommes:");
        totalTests++;
        
        try {
            int idArbreTest = 1;
            Set<String> hommes = GestionLienParenteBDD.recupererHommes(idArbreTest);
            
            if (hommes != null) {
                System.out.println("  - Method returns a non-null set: PASS");
                testsReussis++;
                
                System.out.println("  - Number of men found: " + hommes.size());
                if (!hommes.isEmpty()) {
                    System.out.println("  - Example of men: " + hommes.toString());
                }
            } else {
                System.out.println("  - Method returns null: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests the enregistrerLienDansBDD method.
     * Verifies that the method correctly registers different types of relationships in the database.
     */
    public static void testEnregistrerLienDansBDD() {
        System.out.println("Test enregistrerLienDansBDD:");
        totalTests++;
        
        try {
            // Create test people with null dates
            Personne parent = new Personne("Dupont", "Jean", "Francaise", null, null);
            parent.setId_arbre(1);
            parent.setGenre(Genre.HOMME);
            
            Personne enfant = new Personne("Dupont", "Marie", "Francaise", null, null);
            enfant.setId_arbre(1);
            enfant.setGenre(Genre.FEMME);
            
            // Test PARENT_ENFANT relationship
            GestionLienParenteBDD.enregistrerLienDansBDD(parent, enfant, TypeRelation.PARENT_ENFANT, "Pere");
            System.out.println("  - PARENT_ENFANT relationship registration: PASS");
            
            // Test FRERE_SOEUR relationship
            Personne frere = new Personne("Dupont", "Paul", "Francaise", null, null);
            frere.setId_arbre(1);
            frere.setGenre(Genre.HOMME);
            
            GestionLienParenteBDD.enregistrerLienDansBDD(enfant, frere, TypeRelation.FRERE_SOEUR, "Soeur");
            System.out.println("  - FRERE_SOEUR relationship registration: PASS");
            
            testsReussis++;
            
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests behavior with non-existent tree ID.
     * Verifies that the methods handle non-existent tree IDs gracefully.
     */
    public static void testAvecIdArbreInexistant() {
        System.out.println("Test with non-existent tree ID:");
        totalTests++;
        
        try {
            int idArbreInexistant = 99999;
            Set<String> resultats = GestionLienParenteBDD.recupererPersonnesVivantes(idArbreInexistant);
            
            if (resultats != null && resultats.isEmpty()) {
                System.out.println("  - Method handles non-existent tree ID correctly: PASS");
                testsReussis++;
            } else {
                System.out.println("  - Method does not handle non-existent tree ID correctly: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Tests basic Personne class methods.
     * Verifies the constructor and basic getter methods of the Personne class.
     */
    public static void testMethodesPersonne() {
        System.out.println("Test Personne class methods:");
        totalTests++;
        
        try {
            Personne p = new Personne("Test", "Person", "Francaise", null, null);
            p.setId_arbre(1);
            p.setGenre(Genre.HOMME);
            
            if (p.getNom().equals("Test") && p.getPrenom().equals("Person")) {
                System.out.println("  - Personne constructor and getters: PASS");
                testsReussis++;
            } else {
                System.out.println("  - Personne constructor and getters: FAIL");
                testsEchoues++;
            }
        } catch (Exception e) {
            System.out.println("  - Exception during test: " + e.getMessage() + " - FAIL");
            testsEchoues++;
        }
        System.out.println();
    }
    
    /**
     * Displays a summary of all test results.
     * Shows the total number of tests, passed tests, failed tests, and success rate.
     */
    public static void afficherResultats() {
        System.out.println("=== Test Results Summary ===");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + testsReussis);
        System.out.println("Failed: " + testsEchoues);
        System.out.println("Success rate: " + (testsReussis * 100 / totalTests) + "%");
    }
}