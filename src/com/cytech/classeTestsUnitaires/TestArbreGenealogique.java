package com.cytech.classeTestsUnitaires;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;

import com.cytech.classeProjet.ArbreGenealogique;
import com.cytech.classeProjet.ArbreGenealogiquePanel;
import com.cytech.classeProjet.Cote;
import com.cytech.classeProjet.Genre;
import com.cytech.classeProjet.LienParente;
import com.cytech.classeProjet.Personne;
import com.cytech.classeProjet.TypeRelation;

/**
 * Test class for ArbreGenealogique functionality.
 * This class provides an interactive test environment for building and visualizing
 * a genealogical tree. It allows users to add new persons with various relationships
 * and displays the tree structure graphically.
 */
public class TestArbreGenealogique {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Main method to run the genealogical tree test application.
     * Creates a root person and allows interactive addition of new persons
     * with different relationships. Displays the tree structure graphically.
     *
     * @param args command line arguments (not used)
     * @throws ParseException if date parsing fails
     */
    public static void main(String[] args) throws ParseException {
        System.out.println("Creating root person:");
        Personne racine = creerPersonne();
        ArbreGenealogique arbre = new ArbreGenealogique(racine, 1, new HashSet<>());
        arbre.getPersonnes().add(racine);
    
        LienParente liensParente = new LienParente(arbre);
        arbre.setLiensParente(liensParente);
        while (true) {
            ArbreGenealogique.afficherRelations(arbre);
            JFrame frame = new JFrame("Genealogical Tree");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ArbreGenealogiquePanel(arbre));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println("\nDo you want to add a new person? (yes/no)");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) break;

            System.out.println("Choose a reference person in the tree:");
            Personne reference = choisirPersonne(arbre.getPersonnes());

            if (!arbre.getPersonnes().contains(reference)) {
                System.out.println("Error: reference person does not exist in the tree.");
                continue;
            }

            if (arbre.getRacine().getConjoint() != null && reference.equals(arbre.getRacine().getConjoint())) {
                System.out.println("Addition forbidden: root is married, you cannot add in-laws.");
                continue;
            }

            System.out.println("What is the relationship with this person? (1: parent, 2: child, 3: sibling)");
            int choix = Integer.parseInt(scanner.nextLine());

            Personne nouvelle = creerPersonne();

            boolean existeDeja = arbre.getPersonnes().stream()
                .anyMatch(p -> p.estIdentique(nouvelle));

            if (existeDeja) {
                System.out.println("Error: this person already exists in the tree. Operation cancelled.");
                continue;
            }

            boolean ajoutReussi = false;
            TypeRelation typeRelation = null;

            switch (choix) {
                case 1: // parent
                    if (Personne.aAuMoins18AnsDePlus(nouvelle.getDateNaissance(), reference.getDateNaissance())) {
                        nouvelle.setProfondeur(reference.getProfondeur() + 1);
                        nouvelle.ajouterEnfant(reference,arbre);
                        reference.ajouterParent(nouvelle);
                        ajoutReussi = true;
                        typeRelation = TypeRelation.PARENT_ENFANT;
                        
                    } else {
                        System.out.println("Error: Parent must be older than child.");
                    }
                    break;
                case 2: // child
                    if (Personne.aAuMoins18AnsDePlus(reference.getDateNaissance(), nouvelle.getDateNaissance())) {
                        nouvelle.setProfondeur(reference.getProfondeur() - 1);
                        reference.ajouterEnfant(nouvelle,arbre);
                        nouvelle.ajouterParent(reference);
                        nouvelle.setCote(Cote.NEUTRE);
                        ajoutReussi = true;
                        typeRelation = TypeRelation.PARENT_ENFANT;
                    } else {
                        System.out.println("Error: Child must be younger than parent.");
                    }
                    break;
                case 3: // sibling
                    nouvelle.setProfondeur(reference.getProfondeur());
                    reference.ajouterFrereOuSoeur(nouvelle,arbre);
                    nouvelle.setCote(Cote.NEUTRE);
                    ajoutReussi = true;
                    typeRelation = TypeRelation.FRERE_SOEUR;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (ajoutReussi) {
                arbre.getPersonnes().add(nouvelle);

                // Get relationship name
                String nomRelation = ArbreGenealogique.ObtenirRelationsDepuisRacine(arbre, nouvelle);
                 
                // Add link via the unique LienParente object linked to the tree
                if (typeRelation == TypeRelation.PARENT_ENFANT) {
                    // Ensure source person is parent and destination is child
                    if (reference.getDateNaissance().after(nouvelle.getDateNaissance())) {
                        // reference is younger than nouvelle, so we swap
                        arbre.getLiensParente().ajouterLien(nouvelle, reference, typeRelation, nomRelation);
                    } else {
                        arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
                    }
                } else {
                    // Other types (FRERE_SOEUR, UNION): keep normal order
                    arbre.getLiensParente().ajouterLien(reference, nouvelle, typeRelation, nomRelation);
                }

                System.out.println(nouvelle.getNomComplet() + " has a depth of " + nouvelle.getProfondeur());
            } else {
                System.out.println("Addition cancelled (inconsistent age or other error).");
            }

            // Update automatic relationships (siblings for example)
            for (Personne p : arbre.getPersonnes()) {
                p.detecterFreresEtSoeurs(arbre.getPersonnes(),arbre);
            }
        }

        System.out.println("End of tree construction.");
        // Display tree relationships
        
        System.out.println("\nList of relationships in the tree:");
        ArbreGenealogique.afficherRelations(arbre);
    }
 
    /**
     * Creates a new Personne object by prompting the user for input.
     * Collects information about the person's name, birth date, and gender.
     *
     * @return a new Personne object with the specified attributes
     * @throws ParseException if the date format is invalid
     */
    private static Personne creerPersonne() throws ParseException {
        System.out.print("Last name: ");
        String nom = scanner.nextLine();
        System.out.print("First name: ");
        String prenom = scanner.nextLine();
        System.out.print("Birth date (dd/MM/yyyy): ");
        Date naissance = dateFormat.parse(scanner.nextLine());
        System.out.print("Gender (HOMME/FEMME): ");
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());

        return new Personne(nom, prenom, naissance, null, genre, 1, 0);
    }
    
    /**
     * Allows the user to select a person from a list of persons.
     * Displays a numbered list of persons and returns the selected one.
     *
     * @param personnes the set of persons to choose from
     * @return the selected Personne object
     */
    private static Personne choisirPersonne(Set<Personne> personnes) {
        int i = 1;
        List<Personne> liste = new ArrayList<>(personnes);
        for (Personne p : liste) {
            System.out.println(i + ": " + p.getNomComplet());
            i++;
        }
        int choix = Integer.parseInt(scanner.nextLine());
        return liste.get(choix - 1);
    }
} 